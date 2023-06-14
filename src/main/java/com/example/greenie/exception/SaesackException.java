package com.example.greenie.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaesackException extends RuntimeException {

    private ErrorCode errorCode;
    private String message;

    public SaesackException(ErrorCode errorCode){
        this.errorCode = errorCode;
    }
    public SaesackException(ErrorCode errorCode, String message){
        this.errorCode = errorCode;
        this.message= message;
    }


}
