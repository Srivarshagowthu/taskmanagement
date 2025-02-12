package com.example.demo.Exceptions;

import lombok.Data;

@Data
public class CyborgException extends Exception {

  private String code, message;

  public CyborgException(ErrorCode errorCode) {
    super(errorCode.getErrorMessage());
    this.code = errorCode.name();
    this.message = errorCode.getErrorMessage();
  }

  public CyborgException(ErrorCode code, String... fields) {
    super(String.format(code.getErrorMessage(), fields));
    this.code = code.name();
  }

  public CyborgException(String code, String message) {
    super(message);
    this.code = code;
    this.message = message;
  }
}
