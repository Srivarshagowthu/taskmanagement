package com.ninjacart.task_mgmt_service.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name = "PROCESS_VARIABLE_MAP")
public class ProcessVariableMap extends BaseEntityIntID {

    private Integer processId;

    private Integer variableId;
    private String filter;


}
