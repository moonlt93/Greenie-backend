package com.example.greenie.exception.ErrorCodeImpl;

import com.example.greenie.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductErrorCodeImpl implements ErrorCode {

    NOT_EXIST_HASHTAG("소음태그정보가 들어오지 않았습니다."),
    SIZE_IS_ZERO("리스트 사이즈가 0보다 크지 않습니다."),

    NOT_EXIST_NUMBER("상품이 존재하지 않는 번호입니다");

    private final String description;
}
