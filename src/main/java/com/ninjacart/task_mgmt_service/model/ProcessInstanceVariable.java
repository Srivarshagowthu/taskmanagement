package com.ninjacart.task_mgmt_service.model;


import com.ninjacart.task_mgmt_service.entity.BaseEntityIntID;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import java.util.Date;

@Data
@Entity
@Table(name = "PROCESS_INSTANCE_VARIABLE")
public class ProcessInstanceVariable extends BaseEntityIntID {

    private int processInstanceId;

    private int variableId;

    private String variableValue;
}
