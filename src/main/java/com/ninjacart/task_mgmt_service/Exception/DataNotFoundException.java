package com.ninjacart.task_mgmt_service.Exception;

public class DataNotFoundException extends CyborgException {

    public DataNotFoundException(String message) {
        super("400", message);
    }
}
