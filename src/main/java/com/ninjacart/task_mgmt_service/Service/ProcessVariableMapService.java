package com.ninjacart.task_mgmt_service.Service;

import com.ninjacart.task_mgmt_service.Exception.CyborgException;
import com.ninjacart.task_mgmt_service.model.ProcessVariableMapFilter;
import com.ninjacart.task_mgmt_service.entity.ProcessVariableMap;
import com.ninjacart.task_mgmt_service.entity.Variable;

import java.util.List;

public interface ProcessVariableMapService {

    List<Variable> getVariablesForProcess(int processTask);

    List<ProcessVariableMap> getByFilter(ProcessVariableMapFilter processVariableMapFilter);

    /*List<ProcessVariableMap> createProcessVariableMaps(List<ProcessVariableMap> processVariableMaps)
            throws CyborgException;

    List<ProcessVariableMap> updateProcessVariableMaps(List<ProcessVariableMap> processVariableMaps)
            throws CyborgException;*/
}
