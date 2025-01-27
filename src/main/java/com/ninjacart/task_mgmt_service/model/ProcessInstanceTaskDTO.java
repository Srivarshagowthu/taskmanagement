package com.ninjacart.task_mgmt_service.model;

import com.ninjacart.task_mgmt_service.model.enums.ProcessInstanceTaskStatus;
import com.ninjacart.task_mgmt_service.model.TeamProcessTaskDTO;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class ProcessInstanceTaskDTO {

    public Object workFlowDTO;
    public List<ProcessTaskActionModel> processTaskActions;
    private String comment;
    private Integer classificationId;
    private String classificationName;
    private Integer createdBy;
    private String reference;
    private String referenceType;
    private String processTaskName;
    private Number processTaskId;
    private Number processInstanceTaskId;
    private ProcessInstanceTaskStatus processInstanceTaskStatus;
    private String processName;
    private Integer processId;
    private String requestParam;
    private Map<String, Object> requestJson;
    private int taskId;
    private String taskDescription;
    private String taskName;
    private String processTaskNameAlias;
    private Date createdAt;
    private Date commentCreatedAt;
    private Integer commentCreatedBy;
    private String actionObject;
    private Map<String, Object> actionObjectMap;
    private Date expiryDate;
    private Object object;
    private Integer assignedTo;
    private String pitStatus;
    private String taskCreatedAt;
    private Integer processInstanceId;
    private String timeDiff;
    private Integer priority;
    private List<ProcessInstanceTaskCommentModel> processInstanceTaskCommentsList;
    private Date postponedDate;
    private Date assignedAt;
    private String language;
    private String taskVariable;
    private Number type;
    private Date taskDate;

    private TeamProcessTaskDTO teamProcessTaskDTO;

}
