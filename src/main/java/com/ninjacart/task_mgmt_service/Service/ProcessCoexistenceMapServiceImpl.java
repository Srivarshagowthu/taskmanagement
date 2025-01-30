package com.ninjacart.task_mgmt_service.Service;


import com.ninjacart.task_mgmt_service.Repository.ProcessCoexistenceMapRepository;
import com.ninjacart.task_mgmt_service.entity.Process;
import com.ninjacart.task_mgmt_service.entity.ProcessCoexistenceMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProcessCoexistenceMapServiceImpl implements ProcessCoexistenceMapService {

    @Autowired
    private ProcessCoexistenceMapRepository processCoexistenceMapRepository;

    @Autowired
    private ProcessService processService;

    @Override
    public List<Process> getMappedProcessForProcess(int processId) {
        List<ProcessCoexistenceMap> processCoexistenceMaps = processCoexistenceMapRepository.findByProcessIdAndDeleted(processId, (byte)0);
        List<Integer> mappedProcessIds = processCoexistenceMaps.stream().map(ProcessCoexistenceMap :: getMappedProcessId).collect(Collectors.toList());
        return processService.getProcessesByIds(mappedProcessIds);
    }
}
