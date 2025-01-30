package com.ninjacart.task_mgmt_service.Service;

import com.ninjacart.task_mgmt_service.entity.Variable;

import java.util.List;

public interface VariableService {
    List<Variable> getVariablesByNames(List<String> names);
    Integer getVariableId(String name);
    Variable getVariable(String key);
    List<Variable> getVariablesByIds(List<Integer> variableIds);

}
