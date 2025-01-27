package com.ninjacart.task_mgmt_service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ninjacart.task_mgmt_service.model.enums.LanguagePreferenceEnum;
import com.ninjacart.task_mgmt_service.model.enums.ProcessInstanceTaskStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateProcessInstanceTaskDTO {

    private ProcessTaskModel action;
    private ProcessInstanceTaskStatus updateStatus;
    //private CustomerIssueDto customerIssueDto;
    private List<String> comments;
    private Integer categoryId;
    private Integer classificationId;
    private Object workflowDTO;
    private String processInstanceActionObject;
    private Double latitude;
    private Double longitude;
    private String callbackTime;
    private Map<String, Object> updateActionObjectMap;
    private Date postponedDate;
    private Long postponedDateEpoch;
    private Map<String, String> taskVariables;
    private Map<String,String> processVariables;
    private String postponedToDate; // yyyy-mm-dd format
    private Integer status;
    private Integer escalationStatus;
    private Integer saleOrderId;
    private String completionReason;
    private boolean isNoResponse;
    private Boolean dialogResponse;
    private Integer reasonId;
    private Integer agentId;
    private LanguagePreferenceEnum language;
    private Integer assignedTo;
    private boolean updateTaskVariables;
    private List<Integer> positionCodeIds;
    private String taskDate;
    private List<String> piVariablesToDelete;
}
