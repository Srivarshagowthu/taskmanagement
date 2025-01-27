package com.ninjacart.task_mgmt_service.model.enums;

import java.util.Arrays;
import java.util.List;

public enum ProcessInstanceStatus {

    PENDING(1, "PENDING", Arrays.asList(ProcessInstanceTaskStatus.PENDING)),
    IN_PROGRESS(2, "IN_PROGRESS" ,Arrays.asList(ProcessInstanceTaskStatus.ASSIGNED,
            ProcessInstanceTaskStatus.POSTPONED)),
    EXPIRED(3, "EXPIRED",null),
    COMPLETED(10, "COMPLETED" ,Arrays.asList(ProcessInstanceTaskStatus.COMPLETED, ProcessInstanceTaskStatus.APPROVED)),
    CLOSED(11, "CLOSED" ,Arrays.asList(ProcessInstanceTaskStatus.CLOSED)),
    REJECTED(17,"REJECTED",Arrays.asList(ProcessInstanceTaskStatus.REJECTED));
    private int code;
    private String text;
    private List<ProcessInstanceTaskStatus> processInstanceTaskStatuses;

    ProcessInstanceStatus(int code, String text, List<ProcessInstanceTaskStatus> processInstanceTaskStatuses) {
        this.code = code;
        this.text = text;
        this.processInstanceTaskStatuses = processInstanceTaskStatuses;
    }

    public int getCode() {
        return code;
    }

    public String getText() {
        return text;
    }

    public List<ProcessInstanceTaskStatus> getProcessInstanceTaskStatuses() {
        return processInstanceTaskStatuses;
    }

    public static ProcessInstanceStatus getProcessInstanceStatusByText(String text) {
        return Arrays.stream(ProcessInstanceStatus.values()).filter(each -> each.getText().equalsIgnoreCase(text)).findFirst().orElse(null);
    }

}
