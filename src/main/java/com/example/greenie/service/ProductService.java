package com.example.greenie.service;

import com.example.greenie.dto.ProductDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    void createProduct(ProductDto dto, MultipartFile file) throws IOException;

    ProductDto.Response getProduct(String data);

    List<ProductDto.ResponseList> getAllProduct();
}
