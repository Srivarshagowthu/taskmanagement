package com.ninjacart.task_mgmt_service.entity;


import com.ninjacart.task_mgmt_service.model.enums.ProcessInstanceStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.envers.Audited;


@Audited(auditParents = {BaseEntityIntID.class, BaseEntity.class})
@Entity
@Table(name = "PROCESS_INSTANCE")
@Data
public class ProcessInstance extends BaseEntityIntID  {

    private int processId;

    private String reference;

    @Enumerated(EnumType.STRING)
    private ProcessInstanceStatus status;

    private String actionObject;

    private String language;

    private String referenceType;

}
