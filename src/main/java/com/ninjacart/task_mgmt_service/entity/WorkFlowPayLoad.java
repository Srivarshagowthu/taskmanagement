package com.ninjacart.task_mgmt_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninjacart.task_mgmt_service.model.enums.ProcessInstanceStatus;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkFlowPayLoad {
    private int processId;
    private String reference;
    private String referenceType;
    private Integer priority;
    private Map<String, Object> actionObject;
    private String comment;
    private Integer classificationId;
    private String languagePreference;
    private Date creationDate;
    private Map<String, String> workflowVariables;
    private Map<String, String> taskVariables;
    private Integer type;
    private Integer nextAssignedTo;
    private Date postponedDate;
    //Note: Don't specify IN_PROGRESS status.
    private ProcessInstanceStatus piStatus;
    private boolean autoComplete;
    private boolean disableErrorIfTicketExist;
    private List<Integer> positionCodeIds;
    private Integer processInstanceId;

    @Override
    public String toString() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this);
        } catch (Exception e) {
            return null;
        }
    }
}
