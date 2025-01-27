package com.ninjacart.task_mgmt_service.Service;
import com.ninjacart.task_mgmt_service.Exception.CyborgException;

import java.util.List;

import com.ninjacart.task_mgmt_service.entity.Process;
import com.ninjacart.task_mgmt_service.model.ProcessDTOV2;


public interface ProcessService {
    Process getProcessById(Integer id);

    List<Process> getProcessesByIds(List<Integer> processIds);

    List<Process> getProcessesByNames(List<String> processNames);

    List<Process> createProcesses(List<Process> processes) throws CyborgException;

    List<Process> updateProcess(List<Process> processes) throws CyborgException;

    Process getProcessByName(String name);

    List<Process> getAllProcesses();
    List<ProcessDTOV2> getProcessList();

}


    //ProcessTask getProcessTaskById(int id);

    //List<ProcessDTOV2> getProcessList();
