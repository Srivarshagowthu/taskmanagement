package com.ninjacart.task_mgmt_service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ninjacart.task_mgmt_service.entity.ProcessInstanceTaskCommentClassificationDTO;
import com.ninjacart.task_mgmt_service.model.FieldVerificationCsvDataDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties
public class ProcessInstanceDTO {

    private Integer id;
    private Integer processId;
    private String processName;
    private Integer processInstanceId;
    private Integer processInstanceTaskId;
    private String status;
    private String processInstanceTaskStatus;
    private String reference;
    private String referenceType;
    private Map<String, String> taskVariables;
    private Map<String, String> instanceVariables;
    private List<ProcessInstanceTaskCommentClassificationDTO> commentsAndClassifications;
    private Integer assignedTo;
    private String assignedToUserName;
    private Integer priority;
    private Date taskDate;
    private Date taskEndDate;
    private String actionObject;
    private Map<String, Object> actionObjectMap;
    private Integer processTaskId;
    private Date createdAt;
    private Date updatedAt;
    private Integer createdBy;
    private Integer type;
    private String callFeedback;
    private List<KlawDTO> klawDTOS;
    private Integer auditTicketId;
    private Integer auditScore;
    private String auditComment;
    private List<CustomerStoreImageDTO> auditImageDetails;
    private String auditVideoUrl;
    //    private Boolean showCsvExtractOption;
    private List<FieldVerificationCsvDataDto> fieldVerificationCsvDataDtoList;
    private String language;
    private String positionCodeIds;
    private Integer deleted;
}
