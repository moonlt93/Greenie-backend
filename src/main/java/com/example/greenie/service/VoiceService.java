package com.example.greenie.service;

import com.example.greenie.dto.VoiceDto;

import java.util.List;

public interface VoiceService {




    VoiceDto.Response createAnalysisData(VoiceDto.Request request);

    VoiceDto.Response getAnalysisData(VoiceDto.Request request);

    VoiceDto.Response modifyAnalysisData(VoiceDto.Request request);

    List<VoiceDto.Response> getAllList(String username);
}
