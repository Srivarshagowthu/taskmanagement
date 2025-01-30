package com.ninjacart.task_mgmt_service.Exception;

public class GenericException extends CyborgException {
    public GenericException(String code, String message) {
        super(code, message);
    }
}
