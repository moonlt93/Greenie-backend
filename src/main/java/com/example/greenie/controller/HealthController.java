package com.example.greenie.controller;

import com.example.greenie.dto.HealthDto;
import com.example.greenie.service.HealthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/health")
public class HealthController {

    private final HealthService healthService;



    @PostMapping
    public void createHealth(@RequestPart(value="health") HealthDto.Request dto
                            ,@RequestPart(value="file",required=false)MultipartFile file) throws IOException {

        healthService.createHealth(dto,file);

    }

    @GetMapping
    public ResponseEntity<HealthDto.Response> getList() throws Exception {

        return ResponseEntity.ok(healthService.getAllList());
    }

}
