package com.dokobit.archive.service;

import java.util.Objects;

import static com.dokobit.archive.exception.ValidationErrorCodes.FILE_NAME_MISSING;
import static com.dokobit.archive.exception.ValidationErrorCodes.FILE_SIZE_EXCEEDED;

public class CommonService {
    private static final Long MAX_FILE_SIZE_IN_BYTES = 1000000L;

    protected void validateFileName(String fileName) {
        if (Objects.isNull(fileName) || fileName.isBlank()) {
            FILE_NAME_MISSING.throwError();
        }
    }

    protected void validateFileSize(Long size) {
        if (MAX_FILE_SIZE_IN_BYTES < size) {
            FILE_SIZE_EXCEEDED.throwError();
        }
    }
}
