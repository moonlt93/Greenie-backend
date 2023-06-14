package com.example.greenie.exception.ErrorCodeImpl;

import com.example.greenie.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NoiseErrorCodeImpl implements ErrorCode {

    ALREADY_EXIST_TITLE("해당 소음분류명은 이미 존재합니다."),
    ILLEGAL_ARGUMENT_EXCEPTION("원하는 값이 들어오지 않았습니다."),

    NOT_EXIST_NOISE("해당 소음은 존재하지 않습니다."),
    NOT_EXIST_TITLE("해당 소음분류명이 존재하지 않습니다."),
    NOT_AUTHORIZATION_ACCEPT("잘못된 접근 입니다.");

    private final String description;


}
