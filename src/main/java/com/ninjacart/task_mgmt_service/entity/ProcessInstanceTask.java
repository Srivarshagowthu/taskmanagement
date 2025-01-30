package com.ninjacart.task_mgmt_service.entity;

import com.ninjacart.task_mgmt_service.model.enums.ProcessInstanceTaskStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@EntityListeners(AuditingEntityListener.class)

@Audited(auditParents = {BaseEntityIntID.class, BaseEntity.class})
@Entity
@Table(name = "PROCESS_INSTANCE_TASK")
@Data
public class ProcessInstanceTask extends BaseEntityIntID {
    private int processInstanceId;

    private int processTaskId;

    private Integer assignedTo;

    private Integer nextAssignedTo;

    private Date assignedAt;

    private String name;

    private String description;

    private int type;

    private Integer priority;

    @Enumerated(EnumType.STRING)
    private ProcessInstanceTaskStatus status;

    private Date startDate;

    private Date dueDate;

    private Date endDate;

    private Date postponedDate;

    private Date taskDate;

    private Double latitude;

    private Double longitude;

}
