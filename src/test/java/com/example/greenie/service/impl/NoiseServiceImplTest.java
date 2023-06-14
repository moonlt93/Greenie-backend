package com.example.greenie.service.impl;

import com.example.greenie.domain.Noise;
import com.example.greenie.dto.NoiseDto;
import com.example.greenie.exception.ErrorCodeImpl.NoiseErrorCodeImpl;
import com.example.greenie.repository.NoiseContentRepository;
import com.example.greenie.repository.NoiseRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = {NoiseServiceImpl.class, NoiseRepository.class,NoiseContentRepository.class})
@ActiveProfiles("test")
class NoiseServiceImplTest {


    @Autowired
    NoiseServiceImpl noiseService;

    @MockBean
    NoiseRepository noiseRepository;

    @MockBean
    NoiseContentRepository noiseContentRepository;


    @Test
    @DisplayName("[ 오류 ]소음원분류 부모id가 들어오지 않앗을때")
    void createErrorNoiseSolution() {
        NoiseDto.Request re = NoiseDto.Request.builder()
                .engName("testTile")
                .depth(1)
                .build();

        //given

        Throwable exception = assertThrows(RuntimeException.class, () -> {
            noiseService.createNoiseCategory(re);
        });
        System.out.println(exception.getMessage());
        assertEquals(NoiseErrorCodeImpl.ILLEGAL_ARGUMENT_EXCEPTION.getDescription(),
                exception.getMessage());

    }

    @Test
    void getNoiseSolution() {

        Noise noise = Noise.builder()
                .id(1L)
                .engName("testTitle")
                .build();



        //given
        Mockito.when(noiseRepository.findByEngName("testTitle"))
                .thenReturn(Optional.of(noise));
        //when

        Optional<Noise> optionalNoise = noiseRepository.findByEngName("testTitle");

        Noise noise1 = null;
        if(optionalNoise.isPresent()){
            noise1 = optionalNoise.get();
        }


        //then
        assert noise1 != null;
        Assertions.assertEquals(noise1.getEngName(),"testTitle");
        System.out.println(optionalNoise.get().getEngName());


    }


}