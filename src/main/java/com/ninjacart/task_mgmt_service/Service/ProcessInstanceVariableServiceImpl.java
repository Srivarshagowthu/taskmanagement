package com.ninjacart.task_mgmt_service.Service;

import com.ninjacart.task_mgmt_service.Exception.BadRequestException;
import com.ninjacart.task_mgmt_service.Exception.CyborgException;
import com.ninjacart.task_mgmt_service.Repository.ProcessInstanceVariableRepository;
import com.ninjacart.task_mgmt_service.entity.ProcessInstanceVariable;
import com.ninjacart.task_mgmt_service.entity.Variable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class ProcessInstanceVariableServiceImpl implements ProcessInstanceVariableService{
    @Autowired
    private ProcessInstanceVariableRepository processInstanceVariableRepository;
    @Autowired
    private VariableService variableService;
    @Autowired
    private ProcessVariableMapServiceImpl processVariableMapService;
    @Override
    public List<ProcessInstanceVariable> createProcessInstanceVariables(int userId, int processId, int processInstanceId, Map<String, String> workflowVariables) throws CyborgException {
        if(workflowVariables == null || workflowVariables.isEmpty()) {
            return null;
        }
        List<ProcessInstanceVariable> processInstanceVariables = new ArrayList<>();
        Map<String, Variable> allowedVariablesNameMap = findAllowedVariables(workflowVariables, processId);
        if(allowedVariablesNameMap == null || allowedVariablesNameMap.isEmpty()) {
            throw new BadRequestException("No variables mapped for the process");
        }
        workflowVariables.forEach((variableName, variableValue) -> {
            Variable variable=allowedVariablesNameMap.get(variableName);
            ProcessInstanceVariable processInstanceVariable = constructNewProcessInstanceVariable(userId, processInstanceId, variable.getId(), variableValue);
            processInstanceVariables.add(processInstanceVariable);
        });
        processInstanceVariableRepository.saveAll(processInstanceVariables);
        return processInstanceVariables;
    }
    @Override
    public ProcessInstanceVariable constructNewProcessInstanceVariable(int userId, int processInstanceId, int variableId, String variableValue) {
        ProcessInstanceVariable processInstanceVariable = new ProcessInstanceVariable();
        processInstanceVariable.setProcessInstanceId(processInstanceId);
        processInstanceVariable.setVariableId(variableId);
        processInstanceVariable.setVariableValue(variableValue);
        processInstanceVariable.setDeleted((byte)0);
        return processInstanceVariable;
    }
    private Map<String, Variable> findAllowedVariables(Map<String,String> variableValues,int processId) {
        List<String> variableNames = new ArrayList<>();
        variableValues.forEach((variableName, variableValue) -> {
            variableNames.add(variableName);
        });
        List<Variable> variables = variableService.getVariablesByNames(variableNames);
        Map<String, Variable> variableNameMap = new HashMap<>();
        if(variables != null && !variables.isEmpty()) {
            for(Variable variable : variables) {
                variableNameMap.put(variable.getName(), variable);
            }
        }
        List<Variable> allowedVariablesForProcess = processVariableMapService.getVariablesForProcess(processId);
        Map<String, Variable> allowedVariablesNameMap = new HashMap<>();
        if(allowedVariablesForProcess != null && !allowedVariablesForProcess.isEmpty()) {
            for(Variable allowedVariable : allowedVariablesForProcess) {
                allowedVariablesNameMap.put(allowedVariable.getName(), allowedVariable);
            }
        }

        variableValues.forEach((variableName, variableValue) -> {
            Variable variable = variableNameMap.getOrDefault(variableName, null);
            if(variable == null) {
                return;
            }
            Variable allowedVariable = allowedVariablesNameMap.getOrDefault(variableName, null);
            if(allowedVariable == null) {
                return;
            }
        });
        return allowedVariablesNameMap;
    }
}
