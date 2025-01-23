package com.ninjacart.task_mgmt_service.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle CyborgException and all its subclasses (like BadRequestException)
    @ExceptionHandler(CyborgException.class)
    public ResponseEntity<ErrorResponse> handleCyborgException(CyborgException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getCode(), // Error code from the exception
                ex.getMessage() // Error message from the exception
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST); // Adjust HttpStatus based on your needs
    }
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getCode(), // Error code from the exception
                ex.getMessage() // Error message from the exception
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST); // Adjust HttpStatus based on your needs
    }
    // Handle generic Exception (any other exceptions not caught above)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ErrorCode.UNKNOWN_ERROR.name(), // Use a default error code
                "An unexpected error occurred. Please try again later." // Default message
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
