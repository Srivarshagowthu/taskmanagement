package com.ninjacart.task_mgmt_service.Service;

import com.ninjacart.task_mgmt_service.Exception.CyborgException;
import com.ninjacart.task_mgmt_service.entity.ProcessInstanceVariable;

import java.util.List;
import java.util.Map;

public interface ProcessInstanceVariableService {
    List<ProcessInstanceVariable> createProcessInstanceVariables(int userId, int processId, int processInstanceId, Map<String, String> workflowVariables) throws CyborgException;
    com.ninjacart.task_mgmt_service.entity.ProcessInstanceVariable constructNewProcessInstanceVariable(int userId, int processInstanceId, int variableId, String variableValue);

}
