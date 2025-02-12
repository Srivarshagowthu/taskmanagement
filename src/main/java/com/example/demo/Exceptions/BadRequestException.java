package com.example.demo.Exceptions;

public class BadRequestException extends CyborgException {

  public BadRequestException(String message) {
    super("400", message);
  }
}
