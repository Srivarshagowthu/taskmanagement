package com.product.borg.helper.exception;

import com.product.borg.helper.exception.errorcode.ErrorCode;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data

public class CustomException extends Exception {

    private final String code;
    private final String message;

    public CustomException(ErrorCode errorCode) {
        this(errorCode.name(), errorCode.getErrorMessage());
    }

    public CustomException(ErrorCode code, String... fields) {
        this(code.name(), String.format(code.getErrorMessage(), (Object) fields));
    }

    public CustomException(String code, String message) {
        this.code = code;
        this.message = message;
    }

}