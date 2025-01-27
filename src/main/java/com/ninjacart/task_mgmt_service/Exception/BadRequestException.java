package com.ninjacart.task_mgmt_service.Exception;

public class BadRequestException extends CyborgException {

    public BadRequestException(String message) {
        super("400", message);
    }
}

