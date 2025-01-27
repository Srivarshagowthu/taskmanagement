package com.ninjacart.task_mgmt_service.Service;

import com.ninjacart.task_mgmt_service.entity.Process;

import java.util.List;

public interface ProcessCoexistenceMapService {
    List<Process> getMappedProcessForProcess(int processId);
}
