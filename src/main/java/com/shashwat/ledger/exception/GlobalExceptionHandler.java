package com.shashwat.ledger.exception;

import com.shashwat.ledger.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicatePartyException.class)
    public ApiResponse<Void> handleDuplicateParty(
            DuplicatePartyException ex) {

        return ApiResponse.<Void>builder()
                .data(null)
                .message(ex.getMessage())
                .status(HttpStatus.CONFLICT.value())
                .build();
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<Void> handleGenericException(Exception ex) {

        return ApiResponse.<Void>builder()
                .data(null)
                .message("Something went wrong")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ApiResponse<Void> handleResourceNotFound(
            ResourceNotFoundException ex) {

        return ApiResponse.<Void>builder()
                .data(null)
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .build();
    }
}

