package com.example.soll.backend.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// 전역 에러처리
@RestControllerAdvice
public class GlobalExceptionHandler {

    //예외가 발생하면 예외를 처리
    @ExceptionHandler(APIJsonParsingException.class)
    protected ResponseEntity<ErrorResponse> handleJsonParsingException(APIJsonParsingException ex){
        ErrorCode errorCode = ex.getErrorCode();
        return handleExceptionInternal(errorCode);
    }
    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleUSerNotFoundException(UserNotFoundException ex){
        ErrorCode errorCode = ex.getErrorCode();
        return handleExceptionInternal(errorCode);
    }

    //APIJsonParsingException의 ErrorCode를 사용하여 적절한 HTTP 응답을 생성
    private ResponseEntity<ErrorResponse> handleExceptionInternal(ErrorCode errorCode){
        return ResponseEntity
                .status(errorCode.getHttpStatus().value())
                .body(new ErrorResponse(errorCode));
    }
}

