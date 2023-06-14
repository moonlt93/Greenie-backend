package com.example.greenie.controller;

import com.example.greenie.dto.VoiceDto;
import com.example.greenie.service.VoiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/voice")
public class VoiceController {

private final VoiceService voiceService;


    @PostMapping
    public ResponseEntity<VoiceDto.Response> CreateThings(@RequestBody VoiceDto.Request request){

       VoiceDto.Response res =voiceService.createAnalysisData(request);
        return new ResponseEntity<>(res, HttpStatusCode.valueOf(201));
    }

    @GetMapping
    public VoiceDto.Response getVoiceAnalysis(@RequestBody VoiceDto.Request request){
        return voiceService.getAnalysisData(request);
    }

    @PutMapping
    public VoiceDto.Response modifyVoiceAnalysis(@RequestBody VoiceDto.Request request){
        return voiceService.modifyAnalysisData(request);
    }

    @GetMapping("/list")
    public ResponseEntity<List<VoiceDto.Response>>getAllList(@RequestBody VoiceDto.Request request){
        String username = request.getUsername();
        return ResponseEntity.ok(voiceService.getAllList(username));

    }






}
