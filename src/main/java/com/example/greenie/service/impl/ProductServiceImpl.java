package com.example.greenie.service.impl;

import com.example.greenie.domain.HashTag;
import com.example.greenie.domain.Product;
import com.example.greenie.dto.ProductDto;
import com.example.greenie.exception.SaesackException;
import com.example.greenie.repository.HashTagRepository;
import com.example.greenie.repository.ProductRepository;
import com.example.greenie.service.ProductService;
import com.example.greenie.service.S3Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static com.example.greenie.exception.ErrorCodeImpl.ProductErrorCodeImpl.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {


    private final ProductRepository productRepository;
    private final HashTagRepository hashTagRepository;
    private final S3Service s3Service;

    @Transactional
    @Override
    public void createProduct(ProductDto dto, MultipartFile file) throws IOException {

        String url = s3Service.uploadFile(file);

        Optional<Product> optionalProduct = productRepository.findByProductName(dto.getProductName());

        if (optionalProduct.isPresent()) {

            Product product = optionalProduct.get();
            product.setHash(new ArrayList<>());
            product.addHashName(dto.getHashTagName());

            HashTag tag = new HashTag();
            tag.setHashTagName(dto.getHashTagName());

            product.getHash().add(tag);
            tag.setProduct(product);


        } else {
            Product product = Product.builder()
                    .productName(dto.getProductName())
                    .description(dto.getDescription())
                    .imageUrl(url)
                    .imageLink(dto.getImageLink())
                    .hashName(dto.getHashTagName() + ",")
                    .hash(new ArrayList<>())
                    .build();


            HashTag tag = new HashTag();
            tag.setHashTagName(dto.getHashTagName());


            product.getHash().add(tag);
            tag.setProduct(product);


            productRepository.save(product);
        }
    }

    //상품 갯수에 종속적
    @Override
    public ProductDto.Response getProduct(String data) {


        if (data == null || " ".equals(data)) {
            throw new SaesackException(NOT_EXIST_HASHTAG, NOT_EXIST_HASHTAG.getDescription());
        }


            List<Product> pro = productRepository.findAll();
            HashSet<Long> set = new HashSet<>();

            for (Product product : pro) {
                set.add(product.getId());
            }


            String[] hashTags = data.split(",");
            HashMap<Long, Integer> numberCheck = new HashMap<>();

            for (String hashTag : hashTags) {

                String[] noise = hashTag.split(" ");

                List<HashTag> hashTagOptional = hashTagRepository.findAllByHashTagName(noise[0]);


                if (hashTagOptional.size() > 0) {
                    for (HashTag tag : hashTagOptional) {
                        Long newId = tag.getProduct().getId();
                        numberCheck.put(newId, numberCheck.getOrDefault(newId, 0) + 1);
                    }

                }
            }

            // 해당하는 추천 상품번호를 List에 담는다.
            // set은 해당하지 않는 상품번호
            List<Node> numberLists = new ArrayList<>();
            for (Map.Entry<Long, Integer> maps : numberCheck.entrySet()
            ) {
                numberLists.add(new Node(maps.getKey(), maps.getValue()));
                set.remove(maps.getKey());
            }

        //만약 상품이 4개보다 적다면
        listSizeMaker(numberLists, set);


        numberLists.sort(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return o2.count - o1.count;
            }
        });


        List<Product> list = new ArrayList<>();
        List<Map<Integer, String>> mapList = new ArrayList<>();

        for (int i = 0; i < 2; i++) {

            Optional<Product> optionalProduct = productRepository.findById(numberLists.get(i).no);
            HashMap<Integer, String> maps = new HashMap<>();

            if (optionalProduct.isPresent()) {
                Product node = Product.builder()
                        .imageUrl(optionalProduct.get().getImageUrl())
                        .imageLink(optionalProduct.get().getImageLink())
                        .productName(optionalProduct.get().getProductName())
                        .description(optionalProduct.get().getDescription())
                        .build();

                list.add(node);

                String[] names = optionalProduct.get().getHashName().split(",");
                for (int j = 0; j < names.length; j++) {
                    maps.put(j + 1, names[j]);
                }
                mapList.add(maps);
            }
        }


        return ProductDto.ResponseOf(list, mapList);

    }

    @Override
    public List<ProductDto.ResponseList> getAllProduct() {
        return ProductDto.DtoOf(productRepository.findAll());
    }


    private void listSizeMaker(List<Node> numberLists, HashSet<Long> set) {

        if (numberLists.size() < 2) {
            int count = 2 - numberLists.size();

            while (count != 0) {

                Long numbers = (long) (Math.random() * 10 + 1);

                if (set.contains(numbers)) {
                    count--;
                    numberLists.add(new Node(numbers, 0));
                    set.remove(numbers);
                }

            }
        }

    }


    private static class Node {
        Long no;
        int count;

        public Node(Long no, int count) {
            this.no = no;
            this.count = count;
        }

    }


}


