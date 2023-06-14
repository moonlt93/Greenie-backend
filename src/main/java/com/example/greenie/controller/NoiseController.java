package com.example.greenie.controller;

import com.example.greenie.dto.NoiseAllDto;
import com.example.greenie.dto.NoiseDto;
import com.example.greenie.service.NoiseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/noise")
public class NoiseController {


    private final NoiseService noiseService;

    @PostMapping
    public ResponseEntity<?> createNoiseCategory(@RequestBody NoiseDto.Request request){
        noiseService.createNoiseCategory(request);
        return new ResponseEntity<>(HttpStatusCode.valueOf(201));
    }


    @PutMapping
    public void modifyNoiseCategory(@RequestPart("noise") NoiseDto.Request request,
                                    @RequestPart(value = "file",required = false)MultipartFile file)throws IOException {
        noiseService.modifyNoiseCategory(request,file);
    }

    @GetMapping
    public ResponseEntity<NoiseDto.Response> getList(@RequestBody NoiseDto.Request request) throws Exception {
        return ResponseEntity.ok(noiseService.getAnalysisData(request.getAnalysisData()));
    }


    @GetMapping("/list")
    public ResponseEntity<List<NoiseAllDto>> getAllList() throws Exception {

            return ResponseEntity.ok(noiseService.getAllList());
    }

    @DeleteMapping
    public void deleteNoise(@RequestBody NoiseDto.Request req){
       Long ids = noiseService.deleteThings(req.getId(),req.getSubTitle());
       log.info("[삭제된 id는] : "+ids);
    }

}
