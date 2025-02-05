package com.varsha.process.delegate;

import com.varsha.process.api.ProcessApiDelegate;
import com.varsha.process.dto.Process;
import org.springframework.http.ResponseEntity;

public class ProcessDelegateImpl implements ProcessApiDelegate {

    @Override
    public ResponseEntity<Void> createProcess(Process process) {
        return ProcessApiDelegate.super.createProcess(process);
    }

    @Override
    public ResponseEntity<Void> deleteProcess(Integer id) {
        return ProcessApiDelegate.super.deleteProcess(id);
    }

    @Override
    public ResponseEntity<Process> getProcess(Integer id) {
        return ProcessApiDelegate.super.getProcess(id);
    }

    @Override
    public ResponseEntity<Void> updateProcess(Integer id, Process process) {
        return ProcessApiDelegate.super.updateProcess(id, process);
    }
}
