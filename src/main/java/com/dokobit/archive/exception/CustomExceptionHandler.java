package com.dokobit.archive.exception;

import com.dokobit.archive.service.StatisticsLogService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;

import static com.dokobit.archive.exception.ValidationErrorCodes.FILE_SIZE_EXCEEDED;

@ControllerAdvice
@AllArgsConstructor
public class CustomExceptionHandler {

    private final StatisticsLogService statisticsLogService;

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public void handleMaxSizeException(MaxUploadSizeExceededException exc, HttpServletRequest httpServletRequest) {
        statisticsLogService.logRequest(httpServletRequest.getRequestURI(), httpServletRequest.getRemoteAddr());
        FILE_SIZE_EXCEEDED.throwError();
    }

    @ExceptionHandler(ValidationErrorException.class)
    public ResponseEntity<Object> handleValidationErrorException(ValidationErrorException exc) {
        return new ResponseEntity<>(exc.getResponse(), new HttpHeaders(), exc.getHttpStatus());
    }
}
