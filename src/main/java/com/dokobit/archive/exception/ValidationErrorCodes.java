package com.dokobit.archive.exception;

import org.springframework.http.HttpStatus;

public enum ValidationErrorCodes {
    FILE_NAME_MISSING(1000, "File name is missing."),
    FILE_SIZE_EXCEEDED(1001, "File too large."),
    FILE_ARCHIVE_WENT_WRONG(1002, "Failed to archive files.");

    private final HttpStatus httpStatus;
    private final Integer code;
    private final String message;

    ValidationErrorCodes(Integer code, String message) {
        this.httpStatus = HttpStatus.BAD_REQUEST;
        this.code = code;
        this.message = message;
    }

    public void throwError() {
        throw new ValidationErrorException(this.httpStatus, this.code, this.message);
    }


}
