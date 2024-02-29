package com.gft.commercial.infrastucture.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody ApiError handleValidationException(MethodArgumentTypeMismatchException exception) {
        logger.error("Validation error occurred: {}", exception.getMessage());
        return ApiError.builder().errorCode("VALIDATION_ERROR")
                .message(String.format("Invalid parameter: %s.", exception.getName())).build();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public @ResponseBody ApiError handleNotFoundException(ResourceNotFoundException exception) {
        logger.error("Resource not found: {}", exception.getMessage());
        return ApiError.builder().errorCode(exception.getErrorCode()).message(exception.getMessage()).build();
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleException(RuntimeException exception) {
        logger.error("General error occurred: ", exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiError.builder().errorCode("INTERNAL_ERROR").message(exception.getMessage()).build());
    }
}
