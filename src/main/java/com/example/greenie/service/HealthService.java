package com.example.greenie.service;

import com.example.greenie.dto.HealthDto;
import jakarta.transaction.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface HealthService {
    void createHealth(HealthDto.Request dto, MultipartFile file) throws IOException;

/*    @Transactional
    void createHealth(HealthDto.Request dto);*/

    HealthDto.Response getAllList() throws Exception;
}
