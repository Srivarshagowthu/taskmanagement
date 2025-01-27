package com.ninjacart.task_mgmt_service.model.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ProcessInstanceTaskStatus {

    PENDING(1, "PENDING"),
    ASSIGNED(2, "ASSIGNED"),
    REASSIGNED(3, "REASSIGNED"),
    COMPLETED(10, "COMPLETED"),
    CLOSED(11, "CLOSED"),
    POSTPONED(12, "POSTPONED"),
    EXPIRED(13, "EXPIRED"),
    APPROVED(14, "APPROVED"),
    SENT_TO_LENDER(15, "SENT_TO_LENDER"),
    DISBURSED(16, "DISBURSED"),
    REJECTED(17,"REJECTED");


    static {
        PENDING.nextProcessInstanceTaskStatus = Arrays.asList(ProcessInstanceTaskStatus.ASSIGNED,ProcessInstanceTaskStatus.REJECTED);
        ASSIGNED.nextProcessInstanceTaskStatus = Arrays.asList(ProcessInstanceTaskStatus.POSTPONED, ProcessInstanceTaskStatus.COMPLETED,ProcessInstanceTaskStatus.REJECTED);
        REASSIGNED.nextProcessInstanceTaskStatus = null;
        COMPLETED.nextProcessInstanceTaskStatus = Arrays.asList(ProcessInstanceTaskStatus.CLOSED);
        CLOSED.nextProcessInstanceTaskStatus = null;
        POSTPONED.nextProcessInstanceTaskStatus = Arrays.asList(ProcessInstanceTaskStatus.ASSIGNED);
    }

    private int order;
    private String text;
    private List<ProcessInstanceTaskStatus> nextProcessInstanceTaskStatus;

    ProcessInstanceTaskStatus(int order, String text) {
        this.order = order;
        this.text = text;
    }

    public static List<ProcessInstanceTaskStatus> getEnumsFromStringArray(String[] arr) {
        List<ProcessInstanceTaskStatus> processInstanceTaskStatuses = new ArrayList<>();
        ProcessInstanceTaskStatus[] pitEnums = ProcessInstanceTaskStatus.values();
        Arrays.stream(arr).forEach(s -> Arrays.stream(pitEnums).filter(processInstanceTaskStatus -> processInstanceTaskStatus.name().equalsIgnoreCase(s)).findFirst().ifPresent(processInstanceTaskStatuses::add));
        return processInstanceTaskStatuses;
    }

    public static List<String> getNames(List<ProcessInstanceTaskStatus> processInstanceTaskStatuses) {
        return processInstanceTaskStatuses.stream().map(Enum::name).collect(Collectors.toList());
    }

    public static boolean contains(ProcessInstanceTaskStatus status) {
        return Arrays.stream(ProcessInstanceTaskStatus.values()).anyMatch(processInstanceTaskStatus -> status == processInstanceTaskStatus);
    }

    public int getOrder() {
        return order;
    }

    public String getText() {
        return text;
    }

    public List<ProcessInstanceTaskStatus> getNextProcessInstanceTaskStatus() {
        return nextProcessInstanceTaskStatus;
    }

    public static ProcessInstanceTaskStatus getProcessInstanceTaskStatusByText(String text) {
        return Arrays.stream(ProcessInstanceTaskStatus.values()).filter(each -> each.getText().equalsIgnoreCase(text)).findFirst().orElse(null);
    }
}
