package com.ninjacart.task_mgmt_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;


import java.util.Date;

@Data
@Entity
@Table(name = "PROCESS_COEXISTENCE_MAP")
public class ProcessCoexistenceMap extends BaseEntityIntID {

    private int processId;

    private int mappedProcessId;

}
