package com.ninjacart.task_mgmt_service.Service;

import com.ninjacart.task_mgmt_service.Repository.ProcessVariableMapRepositoryCustom;
import com.ninjacart.task_mgmt_service.entity.ProcessVariableMap;
import com.ninjacart.task_mgmt_service.model.ProcessVariableMapFilter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.index.qual.SameLen;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
@Slf4j
public class ProcessVariableMapRepositoryCustomImpl implements ProcessVariableMapRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;
    public ProcessVariableMapRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public List<ProcessVariableMap> findByFilter(ProcessVariableMapFilter processVariableMapFilter) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProcessVariableMap> criteriaQuery =
                criteriaBuilder.createQuery(ProcessVariableMap.class);
        Root<ProcessVariableMap> root = criteriaQuery.from(ProcessVariableMap.class);

        List<Predicate> predicateList = new ArrayList<>();

        predicateList.add(criteriaBuilder.equal(root.get("deleted"), (byte) 0));

        if (processVariableMapFilter.getProcessIds() != null && !processVariableMapFilter.getProcessIds().isEmpty()) {
            predicateList.add(root.get("processId").in(processVariableMapFilter.getProcessIds()));
        }

        if (processVariableMapFilter.getVariableIds() != null && !processVariableMapFilter.getVariableIds().isEmpty()) {
            predicateList.add(root.get("variableId").in(processVariableMapFilter.getVariableIds()));
        }

        if (predicateList.size() > 1) {
            criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));
        } else {
            // Both process ids and variable ids are null or empty
            log.error("Both list of process ids and variable ids cannot be null or empty");
            throw new IllegalArgumentException("Both list of process ids and variable ids cannot be null or empty");
        }

        criteriaQuery.select(root);
        TypedQuery<ProcessVariableMap> query = entityManager.createQuery(criteriaQuery);

        return query.getResultList();
    }
}
