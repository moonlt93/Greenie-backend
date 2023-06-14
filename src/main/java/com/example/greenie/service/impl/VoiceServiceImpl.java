package com.example.greenie.service.impl;

import com.example.greenie.domain.Voice;
import com.example.greenie.dto.VoiceDto;
import com.example.greenie.exception.ErrorCodeImpl.VoiceErrorCodeImpl;
import com.example.greenie.exception.SaesackException;
import com.example.greenie.repository.VoiceRepository;
import com.example.greenie.service.VoiceService;
import com.example.greenie.type.VoiceStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.greenie.dto.VoiceDto.of;

@Service
@RequiredArgsConstructor
public class VoiceServiceImpl implements VoiceService {

    private final VoiceRepository voiceRepository;

    @Override
    public VoiceDto.Response createAnalysisData(VoiceDto.Request request) {

    Optional<Voice> optionalVoice = voiceRepository.findByUsernameAndTitle(request.getUsername(),request.getTitle());

        if(optionalVoice.isPresent()) {
            throw new SaesackException(VoiceErrorCodeImpl.AlREADY_EXIST_DATA,
                                       VoiceErrorCodeImpl.AlREADY_EXIST_DATA.getDescription());
        }

        Voice voices = Voice.builder()
                .username(request.getUsername())
                .bell(request.getBell())
                .title(request.getTitle())
                .status(VoiceStatus.USING)
                .analysisData(request.getAnalysisData())
                .build();


        voiceRepository.save(voices);

        return VoiceDto.Response.builder()
                .username(request.getUsername())
                .bell(request.getBell())
                .item(of(request.getAnalysisData()))
                .title(request.getTitle())
                .build();
    }

    @Override
    public VoiceDto.Response getAnalysisData(VoiceDto.Request request) {

        Voice voice = voiceRepository.findByUsernameAndTitle(request.getUsername(),request.getTitle())
                .orElseThrow(() -> new SaesackException(VoiceErrorCodeImpl.NOT_EXIST_DATA,
                                                        VoiceErrorCodeImpl.NOT_EXIST_DATA.getDescription()));

        return VoiceDto.Response.builder()
                .dataId(voice.getDataId())
                .username(voice.getUsername())
                .bell(voice.getBell())
                .item(of(voice.getAnalysisData()))
                .title(voice.getTitle())
                .createdAt(voice.getCreatedAt())
                .updatedAt(voice.getUpdatedAt())
                .build();
    }

    @Override
    public List<VoiceDto.Response> getAllList(String username) {
          List<Voice> voice = voiceRepository.findAllByUsername(username);
          return VoiceDto.off(voice);
    }

    @Transactional
    @Override
    public VoiceDto.Response modifyAnalysisData(VoiceDto.Request request) {

        Optional<Voice> optionalVoice = voiceRepository.findById(request.getDataId());
        //예외처리 생각

        if(optionalVoice.isPresent()){

            Voice voice = optionalVoice.get();
            voice.setAnalysisData(request.getAnalysisData());
            voice.setBell(request.getBell());


            voice.update(voice);


            return VoiceDto.Response.builder()
                    .dataId(voice.getDataId())
                    .username(voice.getUsername())
                    .bell(voice.getBell())
                    .item(of(voice.getAnalysisData()))
                    .title(voice.getTitle())
                    .createdAt(voice.getCreatedAt())
                    .updatedAt(voice.getUpdatedAt())
                    .build();
        }else{

            throw new SaesackException(VoiceErrorCodeImpl.NOT_EXIST_DATA,
                                       VoiceErrorCodeImpl.NOT_EXIST_DATA.getDescription());
        }


    }
}
