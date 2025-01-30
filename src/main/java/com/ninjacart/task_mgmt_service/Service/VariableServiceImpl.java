package com.ninjacart.task_mgmt_service.Service;

import com.ninjacart.task_mgmt_service.Repository.VariableRepository;
import com.ninjacart.task_mgmt_service.entity.Variable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class VariableServiceImpl implements VariableService{
    @Autowired
    private VariableRepository variableRepository;

    @Override
    public Variable getVariable(String name) {
        return variableRepository.findByNameAndDeleted(name, (byte)0);
    }

    @Override
    public Integer getVariableId(String name) {
        return getVariable(name).getId();
    }
    @Override
    public List<Variable> getVariablesByNames(List<String> names) {
        return variableRepository.findByNameInAndDeleted(names, (byte)0);
    }
    @Override
    public List<Variable> getVariablesByIds(List<Integer> variableIds) {
        return variableRepository.findByIdInAndDeleted(variableIds, (byte)0);
    }

}
