package com.ninjacart.task_mgmt_service.Service;

import com.ninjacart.task_mgmt_service.Repository.GenericCustomRepo;
import com.ninjacart.task_mgmt_service.Repository.ProcessInstanceRepositoryCustom;
import com.ninjacart.task_mgmt_service.entity.ProcessInstance;
import com.ninjacart.task_mgmt_service.model.ProcessIdFilterDTO;
import com.ninjacart.task_mgmt_service.model.ProcessInstanceDTO;
import com.ninjacart.task_mgmt_service.model.StakeholderProcessInstanceDTO;
import com.ninjacart.task_mgmt_service.model.enums.ProcessInstanceStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ProcessInstanceRepositoryImpl extends GenericCustomRepo implements ProcessInstanceRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ProcessInstance> getProcessInstances(List<Integer> processIds, List<ProcessInstanceStatus> statusList,
                                                     List<String> references, Integer limit, Integer offset,
                                                     String fromDate, String toDate, List<String> referenceTypeList) {
        String hql = buildBaseQueryForProcessInstances(statusList, processIds, references, referenceTypeList, fromDate, toDate);

        Query query = currentSession().createQuery(hql);

        setQueryParameters(query, statusList, references, referenceTypeList, processIds, fromDate, toDate);

        if (offset != null) query.setFirstResult(offset);
        if (limit != null) query.setMaxResults(limit);

        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }

    private String buildBaseQueryForProcessInstances(List<ProcessInstanceStatus> statusList, List<Integer> processIds,
                                                     List<String> references, List<String> referenceTypeList,
                                                     String fromDate, String toDate) {
        StringBuilder hql = new StringBuilder("SELECT pi FROM ProcessInstance pi WHERE pi.deleted = 0");

        if (!CollectionUtils.isEmpty(statusList)) hql.append(" AND pi.status IN (:status)");
        if (!CollectionUtils.isEmpty(processIds)) hql.append(" AND pi.processId IN (:processId)");
        if (!CollectionUtils.isEmpty(references)) hql.append(" AND pi.reference IN (:references)");
        if (!CollectionUtils.isEmpty(referenceTypeList)) hql.append(" AND pi.referenceType IN (:REFERENCE_TYPE)");
        if (fromDate != null) hql.append(" AND date(pi.createdAt) >= date(:FROM_DATE)");
        if (toDate != null) hql.append(" AND date(pi.createdAt) <= date(:TO_DATE)");

        return hql.toString();
    }

    private void setQueryParameters(Query query, List<ProcessInstanceStatus> statusList, List<String> references,
                                    List<String> referenceTypeList, List<Integer> processIds, String fromDate, String toDate) {
        if (!CollectionUtils.isEmpty(statusList)) query.setParameter("status", statusList);
        if (!CollectionUtils.isEmpty(references)) query.setParameter("references", references);
        if (!CollectionUtils.isEmpty(referenceTypeList)) query.setParameter("REFERENCE_TYPE", referenceTypeList);
        if (!CollectionUtils.isEmpty(processIds)) query.setParameter("processId", processIds);
        if (fromDate != null) query.setParameter("FROM_DATE", fromDate);
        if (toDate != null) query.setParameter("TO_DATE", toDate);
    }

    @Override
    @Transactional
    public List<StakeholderProcessInstanceDTO> getActiveProcessForStakeholder(int stakeholderId, String domainName,
                                                                              List<Integer> ignoreProcessIds, Integer thresholdMinutes) {
        String sql = buildActiveProcessQueryForStakeholder(ignoreProcessIds);
        Query query = entityManager.createNativeQuery(sql, "StakeholderProcessInstanceDTOMapping");

        query.setParameter("domainName", domainName);
        query.setParameter("stakeholderId", stakeholderId);

        if (ignoreProcessIds != null && !ignoreProcessIds.isEmpty()) {
            query.setParameter("IGNORE_PROCESS_IDS", ignoreProcessIds);
        }

        return query.getResultList();
    }

    private String buildActiveProcessQueryForStakeholder(List<Integer> ignoreProcessIds) {
        StringBuilder sql = new StringBuilder()
                .append("SELECT pi.id AS processId, p.name AS processName, pi.id AS processInstanceId, ")
                .append("pi.status AS processInstanceStatus, pi.createdAt AS createdTime ")
                .append("FROM PROCESS_INSTANCE pi ")
                .append("JOIN PROCESS_INSTANCE_VARIABLE piv ON piv.ProcessInstanceId = pi.id AND piv.VariableId = 4 ")
                .append("JOIN PROCESS p ON p.id = pi.processId AND p.Domain = :DOMAIN_NAME AND p.deleted = 0 ")
                .append("WHERE piv.VariableValue = :STAKEHOLDER_ID AND pi.status IN ('PENDING', 'IN_PROGRESS')");

        if (ignoreProcessIds != null && !ignoreProcessIds.isEmpty()) {
            sql.append(" AND p.id NOT IN (:IGNORE_PROCESS_IDS)");
        }

        return sql.toString();
    }

    @Override
    public List<ProcessInstanceDTO> findOpenPIByReferenceAndExternalReferenceForOndc(String reference, String externalReference) {
        String sql = "SELECT pi.id AS processInstanceId, pit.id AS processInstanceTaskId " +
                "FROM workflow.PROCESS_INSTANCE pi " +
                "JOIN workflow.PROCESS_INSTANCE_TASK pit ON pi.id = pit.ProcessInstanceId " +
                "WHERE pi.Reference = :reference " +
                "AND pit.Type = 1 " +
                "AND pi.ProcessId = 160 " +
                "AND pi.Deleted = 0 " +
                "AND pit.Deleted = 0 " +
                "AND JSON_UNQUOTE(JSON_EXTRACT(pi.ActionObject, '$.externalReferenceId')) = :externalReference " +
                "AND pi.Status IN ('PENDING', 'IN_PROGRESS')";

        Query query = entityManager.createNativeQuery(sql, "ProcessInstanceDTOMapping");
        query.setParameter("reference", reference);
        query.setParameter("externalReference", externalReference);

        return query.getResultList();
    }

    private String fetchQueryByProcessIdFilterDtos(List<ProcessIdFilterDTO> processIdBasedFilterDTO) {
        List<String> queries = new ArrayList<>();
        for (int i = 0; i < processIdBasedFilterDTO.size(); i++) {
            String query = constructQueryFromProcessIdFilterDto(processIdBasedFilterDTO.get(i), i);
            if (!StringUtils.isEmpty(query)) {
                queries.add("(" + query + ")");
            }
        }

        return StringUtils.isEmpty(String.join(" OR ", queries)) ? "" : "(" + String.join(" OR ", queries) + ")";
    }

    private String constructQueryFromProcessIdFilterDto(ProcessIdFilterDTO processIdFilterDTO, int counter) {
        List<String> queryParts = new ArrayList<>();

        if (processIdFilterDTO != null) {
            if (!CollectionUtils.isEmpty(processIdFilterDTO.getProcessIds())) {
                queryParts.add("pi.processId IN (:PROCESS_ID_" + counter + ")");
            }
            if (!CollectionUtils.isEmpty(processIdFilterDTO.getAssignedToIds())) {
                queryParts.add("pit.assignedTo IN (:ASSIGNED_TO_" + counter + ")");
            }
            if (!CollectionUtils.isEmpty(processIdFilterDTO.getReferences())) {
                queryParts.add("pi.reference IN (:REFERENCE_" + counter + ")");
            }
            if (!CollectionUtils.isEmpty(processIdFilterDTO.getProcessInstanceStatus())) {
                queryParts.add("pi.status IN (:PI_STATUS_" + counter + ")");
            }
        }

        return String.join(" AND ", queryParts);
    }

    @Override
    public List<ProcessInstance> fetchOpenPIsByReferenceAndProcess(String reference, Integer processId) {
        String hql = " SELECT pi FROM ProcessInstance pi WHERE pi.processId = :processId and pi.deleted = 0 and pi.status in (:status) and pi.reference = :reference " ;

        Query query = currentSession().createQuery(hql);
        query.setParameter("reference", reference);
        query.setParameter("processId", processId);
        query.setParameter("status", Arrays.asList(ProcessInstanceStatus.PENDING, ProcessInstanceStatus.IN_PROGRESS));

        return query.getResultList();
    }

}
