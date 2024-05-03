package com.example.soll.backend.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// 전역 에러처리
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(APIJsonParsingException.class)
    protected ResponseEntity<ErrorResponse> handleJsonParsingException(APIJsonParsingException ex){
        ErrorCode errorCode = ex.getErrorCode();
        return handleExceptionInternal(errorCode);
    }

    private ResponseEntity<ErrorResponse> handleExceptionInternal(ErrorCode errorCode){
        return ResponseEntity
                .status(errorCode.getHttpStatus().value())
                .body(new ErrorResponse(errorCode));
    }
}

