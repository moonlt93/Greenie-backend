package com.example.greenie.service.impl;

import com.example.greenie.domain.Noise;
import com.example.greenie.domain.NoiseContent;
import com.example.greenie.dto.NoiseAllDto;
import com.example.greenie.dto.NoiseDto;
import com.example.greenie.exception.ErrorCodeImpl.NoiseErrorCodeImpl;
import com.example.greenie.exception.SaesackException;
import com.example.greenie.repository.NoiseContentRepository;
import com.example.greenie.repository.NoiseRepository;
import com.example.greenie.service.NoiseService;
import com.example.greenie.service.S3Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoiseServiceImpl implements NoiseService {


    private final NoiseRepository noiseRepository;
    private final NoiseContentRepository noiseContentRepository;
    private final S3Service s3Service;

    @Override
    public void createNoiseCategory(NoiseDto.Request request) {

        if (request.getParentId() == null && request.getEngName().equals("root")) {

            log.info("여긴 parentId 없을때");
            Optional<Noise> optionalNoise = noiseRepository.findByEngName(request.getEngName());

            Noise parentNoise;

            parentNoise = optionalNoise.orElseGet(() -> Noise.builder()
                    .engName("root")
                    .korName("루트")
                    .depth(0)
                    .subNoise(new ArrayList<>())
                    .build());

            noiseRepository.save(parentNoise);

        } else if (request.getParentId() != null) {

            if (request.getId() != null) {
                Optional<Noise> optionalNoise = noiseRepository.findById(request.getId());

                if (optionalNoise.isPresent()) {
                    log.info("여기 들어왔니?");
                    Noise parentNoise = optionalNoise.get();

                    NoiseContent content = NoiseContent.builder()
                            .subTitle(request.getSubTitle())
                            .content(request.getContent())
                            .contentUrl(request.getContentUrl())
                            .build();

                    content.addNoiseCon(parentNoise);

                    noiseContentRepository.save(content);

                }

            } else {

                log.info("여긴 parentId 있을때");

                Optional<Noise> optionalNoise = noiseRepository.findById(request.getParentId());

                if (optionalNoise.isPresent()) {

                    if (request.getParentId() == 1) {
                        Noise parentNoise = optionalNoise.get();
                        parentNoise.setSubNoise(new ArrayList<>());

                        Noise subNoise = Noise.builder()
                                .korName(request.getKorName())
                                .engName(request.getEngName())
                                .depth(parentNoise.getDepth() + 1)
                                .build();


                        parentNoise.addSubNode(subNoise);

                        noiseRepository.save(parentNoise);

                    } else {

                        Noise parentNoise = optionalNoise.get();
                        parentNoise.setSubNoise(new ArrayList<>());

                        Noise subNoise = Noise.builder()
                                .engName(request.getEngName())
                                .korName(request.getKorName())
                                .depth(parentNoise.getDepth() + 1)
                                .noiseContent(new ArrayList<>())
                                .build();

                        NoiseContent con = NoiseContent.builder()
                                .subTitle(request.getSubTitle())
                                .content(request.getContent())
                                .contentUrl(request.getContentUrl())
                                .build();

                        con.addNoiseCon(subNoise);
                        parentNoise.addSubNode(subNoise);

                        noiseRepository.save(parentNoise);
                    }
                }
            }
        } else {
            throw new SaesackException(NoiseErrorCodeImpl.ILLEGAL_ARGUMENT_EXCEPTION
                    , NoiseErrorCodeImpl.ILLEGAL_ARGUMENT_EXCEPTION.getDescription());
        }

    }

    /* 소음솔루션 문구만 수정. */
    @Transactional
    @Override
    public void modifyNoiseCategory(NoiseDto.Request request, MultipartFile file) throws IOException {

        String url = s3Service.uploadFile(file);
        log.info("[해당 소음ID 확인]: " + request.getId());
        Optional<NoiseContent> optionalNoise = noiseContentRepository.findById(request.getId());

        if (optionalNoise.isPresent()) {
            NoiseContent noise2 = optionalNoise.get();
            noise2.setImageUrl(url);
            noise2.modify(noise2);
        }


    }

    @Override
    public NoiseDto.Response getAnalysisData(String analysisData) throws Exception {

        String[] engList = analysisData.split(",");
        List<Noise> noiseList = new ArrayList<>();
        for (String eng : engList
        ) {
            String[] engSplit = eng.split(" ");
            Optional<Noise> optionalNoise = noiseRepository.findByEngName(engSplit[0]);
            if (optionalNoise.isPresent()) {
                Noise noise = optionalNoise.get();
                noiseList.add(noise);
            }
        }

        return new NoiseDto.Response(ofList(noiseList));
    }


    @Override
    public List<NoiseAllDto> getAllList() throws Exception {

        log.info("[모든 소음 리스트]");
        List<Noise> list = noiseRepository.findAllThings();
        List<NoiseAllDto> resultList = new ArrayList<>();
        for (Noise no : list
        ) {
            Long idx = no.getId();

            // 해당 아이템을 리스트로 뽑아와서  JsonObject에 담는다.
            List<NoiseContent> contentList = noiseContentRepository.findByNoise_Id(idx);
            JSONArray itemObj = createObj(contentList);
            NoiseAllDto dtos = NoiseAllDto.builder()
                    .parentId(no.getParentNoise().getId())
                    .noiseId(idx)
                    .korName(no.getKorName())
                    .engName(no.getEngName())
                    .depth(no.getDepth())
                    .item(itemObj)
                    .build();

            resultList.add(dtos);
        }

        return resultList;


    }


    private static JSONArray createObj(List<NoiseContent> con) throws Exception {

        JSONArray jsonArray = new JSONArray();

        try {

            for (NoiseContent content : con) {
                JSONObject sObj = new JSONObject();
                log.info("[img]" + content.getImageUrl());
                sObj.put("subTitle", content.getSubTitle());
                sObj.put("content", content.getContent());
                sObj.put("img", content.getImageUrl());


                JSONArray sArray = new JSONArray();
                if (content.getContentUrl() != null) {
                    String[] subUrl = urlMaker(content.getContentUrl());

                    for (String val : subUrl
                    ) {
                        JSONObject urlObj = new JSONObject();
                        String[] vals = val.split("##");
                        if (vals.length > 1) {
                            urlObj.put(0, vals[0]);
                            urlObj.put(1, vals[1]);
                        }
                        sArray.add(urlObj);
                    }
                }
                sObj.put("contentUrl", sArray);
                jsonArray.add(sObj);
            }


        } catch (Exception e) {
            throw new Exception(e);
        }
        return jsonArray;
    }

    @Override
    public Long deleteThings(Long idx, String title) {

        Optional<NoiseContent> optionalNoiseContent = noiseContentRepository.findById(idx);

        if (optionalNoiseContent.isPresent() && optionalNoiseContent.get().getSubTitle().equals(title)) {

            NoiseContent noiseContent = optionalNoiseContent.get();
            Long ids = noiseContent.getId();

            noiseContentRepository.delete(noiseContent);


            return ids;
        }

        return null;
    }

    private static String[] urlMaker(String firstTitle) {
        return firstTitle.split("@@");
    }

    private JSONArray ofList(List<Noise> noise) throws Exception {

        JSONArray jsonArray = new JSONArray();
        for (Noise no : noise
        ) {
            JSONObject soj = new JSONObject();
            List<NoiseContent> opCon = noiseContentRepository.findByNoise_Id(no.getId());
            JSONArray itemObj = createObj(opCon);

            soj.put(no.getEngName(), itemObj);
            jsonArray.add(soj);
        }
        return jsonArray;
    }


}
