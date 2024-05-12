package com.example.soll.backend.exception;

import lombok.Getter;

//JSON파싱 중 발생하는 예외
@Getter
public class APIJsonParsingException extends RuntimeException{
    private final ErrorCode errorCode;

    public APIJsonParsingException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
