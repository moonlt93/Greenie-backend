package com.example.greenie.service;

import com.example.greenie.dto.NoiseAllDto;
import com.example.greenie.dto.NoiseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface NoiseService {

    void createNoiseCategory(NoiseDto.Request request);

    void modifyNoiseCategory(NoiseDto.Request request, MultipartFile file) throws IOException;

    List<NoiseAllDto> getAllList() throws Exception;

    Long deleteThings(Long idx,String title);

    NoiseDto.Response getAnalysisData(String analysisData) throws Exception;
}
