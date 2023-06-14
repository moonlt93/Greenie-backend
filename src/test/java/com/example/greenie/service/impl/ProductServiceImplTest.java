package com.example.greenie.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static com.example.greenie.exception.ErrorCodeImpl.ProductErrorCodeImpl.NOT_EXIST_HASHTAG;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@Import({ProductServiceImpl.class})
@ActiveProfiles("test")
class ProductServiceImplTest {


    @InjectMocks
    ProductServiceImpl productService;



    @Test
    @DisplayName("[에러] 추천 상품 조회 데이터x")
    void getList(){
    //given
        String analysisData = " ";

    //when
        Throwable exception = assertThrows(RuntimeException.class,
                    () -> productService.getProduct(analysisData));
        System.out.println(exception.getMessage());

        assertEquals(NOT_EXIST_HASHTAG.getDescription(),exception.getMessage());
    //then
    }

}