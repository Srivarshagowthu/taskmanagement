package com.ninjacart.task_mgmt_service.Service;

import com.ninjacart.task_mgmt_service.Repository.ProcessInstanceHandler;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
@Service
public class ProcessInstanceHandlerFactory extends HandlerFactory<Integer, ProcessInstanceHandler> {
    @Autowired
    private List<? extends ProcessInstanceHandler> processInstanceHandlers;

    @PostConstruct
    void init() {
        initializeHandlerBag();
        build(processInstanceHandlers);
    }

    @Override
    Map<Integer, ProcessInstanceHandler> initializeHandlerBag() {
        return this.handlerBag = new HashMap<>();
    }

    @Override
    void build(List<? extends ProcessInstanceHandler> handlers) {
        for(ProcessInstanceHandler handler : handlers) {
            add(handler.getProcessId(), handler);
        }
    }

    @Override
    void add(Integer key, ProcessInstanceHandler handler) {
        this.handlerBag.put(key, handler);
    }

    @Override
    public ProcessInstanceHandler get(Integer key) {
        return this.handlerBag.getOrDefault(key, null);
    }

}
