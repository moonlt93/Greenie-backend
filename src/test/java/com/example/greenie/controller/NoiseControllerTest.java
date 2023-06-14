package com.example.greenie.controller;

import com.example.greenie.service.NoiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(NoiseController.class)
@AutoConfigureWebMvc // 이 어노테이션을 통해 MockMvc를 Builder 없이 주입 받을 수 있음.
@MockBean(JpaMetamodelMappingContext.class)
@ActiveProfiles("test")
class NoiseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    NoiseService noiseService;

/*

    @SuppressWarnings("unchecked")
    @Test
    @DisplayName("소음 줄이는 방법을 가져오기 테스트")
    void getNoiseSolution() throws Exception {

        NoiseDto.Request request = NoiseDto.Request.builder()
                .analysisData("기타 1234")
                .build();
        JSONObject obj = new JSONObject();

        obj.put("기타", "실내 음압을 높이면 외부에서 들어오거나 내부에서 발생되는 소음을 최소한으로 확산 시킬 수 있어요.");
        String json = new ObjectMapper().writeValueAsString(request);
    //given
    given(noiseService.getNoiseSolutions(any()))
            .willReturn(new NoiseDto.Response(obj));

    //when
    String url = String.format("/api/noise");
    mockMvc.perform(MockMvcRequestBuilders.get(url)
                    .content(json)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.item").exists())
            .andExpect(jsonPath("$.item.기타").value("실내 음압을 높이면 외부에서 들어오거나 내부에서 발생되는 소음을 최소한으로 확산 시킬 수 있어요."))
            .andDo(print());

         //then
        verify(noiseService).getNoiseSolutions(any());
    }

*/


}