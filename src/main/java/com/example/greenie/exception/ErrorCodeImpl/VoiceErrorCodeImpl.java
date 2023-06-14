package com.example.greenie.exception.ErrorCodeImpl;

import com.example.greenie.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VoiceErrorCodeImpl implements ErrorCode {

    AlREADY_EXIST_DATA("해당 제목의 데이터는 이미 있습니다."),
    NOT_EXIST_DATA("데이터가 존재하지 않습니다."),

    ARGUMENT_ERROR("원하는 값이 들어오지 않았습니다.");
    private final String description;


}
