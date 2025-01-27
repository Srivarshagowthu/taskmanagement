package com.ninjacart.task_mgmt_service.Service;


import java.util.List;
import java.util.Map;

public abstract class HandlerFactory<K, H> {
    Map<K, H> handlerBag;
    abstract Map<K,H> initializeHandlerBag();
    abstract void build(List<? extends H> handlers);
    abstract void add(K key, H handler);
    public abstract H get(K key);
}
