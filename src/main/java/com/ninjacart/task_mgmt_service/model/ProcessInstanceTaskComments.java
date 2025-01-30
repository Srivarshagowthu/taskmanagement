package com.ninjacart.task_mgmt_service.model;

import com.ninjacart.task_mgmt_service.entity.BaseEntityIntID;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.util.Date;

@Data
@Entity
@Table(name = "PROCESS_INSTANCE_TASK_COMMENT")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessInstanceTaskComments extends BaseEntityIntID {

    private int processInstanceTaskId;

    private String comment;

    private Integer classificationId;

    private int categoryId;

}
