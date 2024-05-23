package com.example.soll.backend.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    API_JSON_PARSING_ERROR(HttpStatus.NO_CONTENT,"API_JSON_PARSING_ERROR"),
    USER_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND,"USER_NOT_FOUND_EXCEPTION");

    private final HttpStatus httpStatus;
    private final String message;
}
