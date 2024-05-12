package com.example.soll.backend.exception;

import lombok.Getter;

import java.time.LocalDateTime;

//예외 발생시 클라이언트로 반환될 에러 응답 정의
@Getter
public class ErrorResponse {

    private final LocalDateTime timeStamp = LocalDateTime.now();
    private final int statusCode;
    private final String error;
    private final String message;

    public ErrorResponse(ErrorCode errorCode){
        this.statusCode = errorCode.getHttpStatus().value();
        this.error = errorCode.getHttpStatus().name();
        this.message = errorCode.getMessage();
    }
}
