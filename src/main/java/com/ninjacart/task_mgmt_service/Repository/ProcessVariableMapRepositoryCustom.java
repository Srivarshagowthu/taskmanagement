package com.ninjacart.task_mgmt_service.Repository;


import com.ninjacart.task_mgmt_service.model.ProcessVariableMapFilter;
import com.ninjacart.task_mgmt_service.entity.ProcessVariableMap;
import java.util.List;

public interface ProcessVariableMapRepositoryCustom {

    List<ProcessVariableMap> findByFilter(ProcessVariableMapFilter filter);
}
