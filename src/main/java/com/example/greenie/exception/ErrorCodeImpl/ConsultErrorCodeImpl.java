package com.example.greenie.exception.ErrorCodeImpl;

import com.example.greenie.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ConsultErrorCodeImpl implements ErrorCode {

    ALREADY_EXIST_TITLE("해당이름은 이미 존재합니다.");


    private final String description;
}
