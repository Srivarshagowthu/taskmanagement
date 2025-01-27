package com.ninjacart.task_mgmt_service.model;


import java.util.List;

public class AutoComplete {

    private List<String> references;

    private int processId;

    public List<String> getReferences() {
        return references;
    }

    public void setReferences(List<String> reference) {
        this.references = reference;
    }

    public int getProcessId() {
        return processId;
    }

    public void setProcessId(int processId) {
        this.processId = processId;
    }
}
