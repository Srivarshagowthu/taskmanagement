package com.ninjacart.task_mgmt_service.Repository;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public abstract class GenericCustomRepo<E, PK extends Serializable> {

    @PersistenceContext
    private EntityManager entityManager; // No need to specify unitName for the default entity manager

    private Class<E> getPersistenceClass() {
        ParameterizedType superClass = (ParameterizedType) getClass().getGenericSuperclass();
        Class<E> type = (Class<E>) superClass.getActualTypeArguments()[0];
        return type;
    }

    protected Session currentSession() {
        return entityManager.unwrap(Session.class);
    }
}
