package com.ninjacart.task_mgmt_service.Service;

import com.ninjacart.task_mgmt_service.Exception.CyborgException;
import com.ninjacart.task_mgmt_service.entity.ProcessInstanceTask;
import com.ninjacart.task_mgmt_service.entity.WorkFlowPayLoad;
import com.ninjacart.task_mgmt_service.model.enums.ProcessInstanceStatus;

import java.util.Date;
import java.util.List;

public interface ProcessInstanceTaskService {

    List<ProcessInstanceTask> getProcessInstanceTasksByProcessInstanceIds(List<Integer> processInstanceIds);
    ProcessInstanceTask createProcessInstanceTaskForProcessInstance(int userId, WorkFlowPayLoad workFlowPayLoad, Integer processInstanceId, int processId, int orderBy, int assignedTo, Date createdAt) throws CyborgException;

    ProcessInstanceTask createProcessInstanceTask(int userId,
                                                  int assignedTo,
                                                  int processInstanceId,
                                                  int processId,
                                                  int orderBy,
                                                  Integer paramPriority,
                                                  String stakeholderName,
                                                  String stakeholderId,
                                                  Date creationDate,
                                                  Integer type,
                                                  Integer nextAssignedTo,
                                                  Date postponedDate,
                                                  ProcessInstanceStatus piStatus) throws CyborgException;
    //List<ProcessInstanceTask> updateProcessInstanceTasks(List<ProcessInstanceTask> processInstanceTasks);

}
