package com.example.greenie.controller;

import com.example.greenie.dto.VoiceDto;
import com.example.greenie.service.VoiceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VoiceController.class)
//@AutoConfigureWebMvc
@MockBean(JpaMetamodelMappingContext.class)
@ActiveProfiles("test")
class VoiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    VoiceService voiceService;


    @Test
    @DisplayName("소리분석 데이터 생성 테스트")
    void createVoice() throws Exception {
        VoiceDto.Request request = VoiceDto.Request.builder()
                .username("새싹톤")
                .bell(50.0)
                .title("testVoice1")
                .analysisData("절삭기 0.3223,세탁기 0.456,기차 0.5566")
                .build();

        String json = new ObjectMapper().writeValueAsString(request);
    //given
    given(voiceService.createAnalysisData(BDDMockito.any()))
            .willReturn(BDDMockito.any());
    //when

        mockMvc.perform(MockMvcRequestBuilders.post("/api/voice")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(print());

        //then
        verify(voiceService).createAnalysisData(BDDMockito.any());

    }

    @Test
    @DisplayName("데이터 가져오기")
    void getVoiceTest() throws Exception {
    //given

        VoiceDto.Request request = VoiceDto.Request.builder()
                .title("testTitle")
                .bell(10.092)
                .username("test")
                .analysisData("기타 1234")
                .build();

        VoiceDto.Response res = VoiceDto.Response.builder()
                .title("testTitle")
                .bell(10.092)
                .username("test")
                .build();
        String json = new ObjectMapper().writeValueAsString(request);
        //given
        given(voiceService.getAnalysisData(any()))
                .willReturn(res);

        //when
        String url = "/api/voice";
        mockMvc.perform(MockMvcRequestBuilders.get(url)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("test"))
                .andExpect(jsonPath("$.bell").value(10.092))
                .andDo(print());

        //then
        verify(voiceService).getAnalysisData(any());
    }


}