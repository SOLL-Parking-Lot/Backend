package com.example.soll.backend.exception;

import lombok.Getter;

@Getter
public class APIJsonParsingException extends RuntimeException{
    private final ErrorCode errorCode;

    public APIJsonParsingException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
