package com.example.greenie.service.impl;

import com.example.greenie.domain.Voice;
import com.example.greenie.dto.VoiceDto;
import com.example.greenie.repository.VoiceRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Import({VoiceServiceImpl.class})
@ActiveProfiles("test")
class VoiceServiceImplTest {



    @InjectMocks
    VoiceServiceImpl voiceService;


    @Mock
    VoiceRepository voiceRepository;


    @Test
    @DisplayName("[post] 소음 분석데이터 삽입")
    void createVoice() {
        //given
        VoiceDto.Request req = VoiceDto.Request.builder()
                .username("testtest")
                .bell(12.3)
                .title("testTitleTest")
                .analysisData("voice 123,footstep 342")
                .build();

        VoiceDto.Response res = VoiceDto.Response.builder()
                .username("testtest")
                .title("testTitleTest")
                .build();


        //when
        lenient().when(voiceService.createAnalysisData(req))
                .thenReturn(res);

        //then

        VoiceDto.Response mockRes = voiceService.createAnalysisData(req);

        ArgumentCaptor<Voice> captor = ArgumentCaptor.forClass(Voice.class);

        verify(voiceRepository, times(1)).save(captor.capture());

        Assertions.assertEquals(mockRes.getTitle(),"testTitleTest");

    }
}


