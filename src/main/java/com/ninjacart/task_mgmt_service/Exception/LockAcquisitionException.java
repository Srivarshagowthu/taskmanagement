package com.ninjacart.task_mgmt_service.Exception;

public class LockAcquisitionException extends CyborgException {
    public LockAcquisitionException(String message) {

        super("409",message);
    }
}
