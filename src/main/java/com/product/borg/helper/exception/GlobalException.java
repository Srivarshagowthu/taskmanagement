package com.product.borg.helper.exception;

import com.product.borg.model.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse> handleCustomException(CustomException ex) {
        ApiResponse response = new ApiResponse();
        response.setSuccess(false);
        response.setErrorCode(ex.getCode());
        response.setErrorMessage(ex.getMessage());
        response.setStatus(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGenericException(Exception ex) {
        ApiResponse response = new ApiResponse();
        response.setSuccess(false);
        response.setErrorCode("INTERNAL_ERROR");
        response.setErrorMessage("An unexpected error occurred: " + ex.getMessage());
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}