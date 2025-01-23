package com.ninjacart.task_mgmt_service.Service;
import com.ninjacart.task_mgmt_service.Exception.CyborgException;
import com.ninjacart.task_mgmt_service.Exception.GlobalExceptionHandler;
import java.util.List;
import java.util.Optional;

import com.ninjacart.task_mgmt_service.entity.Process;


public interface ProcessService {
    Optional<Process> getProcessById(Integer id);

    List<Process> getProcessesByIds(List<Integer> processIds);

    List<Process> getProcessesByNames(List<String> processNames);

    List<Process> createProcesses(List<Process> processes) throws CyborgException;

    List<Process> updateProcess(List<Process> processes) throws CyborgException;

    Process getProcessByName(String name);

    List<Process> getAllProcesses();
}


    //ProcessTask getProcessTaskById(int id);

    //List<ProcessDTOV2> getProcessList();
