package com.ninjacart.task_mgmt_service.entity;


import com.ninjacart.task_mgmt_service.model.enums.ProcessInstanceStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

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
    @Column(name = "external_reference_for_ondc") // Adjust column name if necessary
    private String externalReferenceForOndc;

    private String language;
    private String referenceType;

}
