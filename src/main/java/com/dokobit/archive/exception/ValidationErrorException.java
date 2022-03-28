package com.dokobit.archive.exception;

import com.dokobit.archive.exception.response.ErrorMessageResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ValidationErrorException extends RuntimeException {
    private final ErrorMessageResponse response;
    private final HttpStatus httpStatus;

    public ValidationErrorException(HttpStatus httpStatus, Integer code, String message) {
        this.httpStatus = httpStatus;
        this.response = new ErrorMessageResponse(code, message);
    }
}
