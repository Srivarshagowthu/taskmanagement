package com.ninjacart.task_mgmt_service.Service;

import com.ninjacart.task_mgmt_service.Repository.ProcessVariableMapRepository;
import com.ninjacart.task_mgmt_service.entity.ProcessVariableMap;
import com.ninjacart.task_mgmt_service.entity.Variable;
import com.ninjacart.task_mgmt_service.model.ProcessVariableMapFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class ProcessVariableMapServiceImpl implements ProcessVariableMapService{
    @Autowired
    private ProcessVariableMapRepository processVariableMapRepository;

    @Autowired
    private VariableService variableService;
    @Override
    public List<Variable> getVariablesForProcess(int processId) {
        List<ProcessVariableMap> processVariableMaps = processVariableMapRepository.findByProcessIdAndDeleted(
                processId, (byte) 0);
        if (processVariableMaps == null || processVariableMaps.isEmpty()) {
            return null;
        }
        List<Integer> variableIds = processVariableMaps.stream().map(ProcessVariableMap::getVariableId)
                .collect(Collectors.toList());
        return variableService.getVariablesByIds(variableIds);
    }
    @Override
    public List<ProcessVariableMap> getByFilter(ProcessVariableMapFilter processVariableMapFilter) {
        return processVariableMapRepository.findByFilter(processVariableMapFilter);
    }

}
