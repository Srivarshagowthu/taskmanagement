package com.ninjacart.task_mgmt_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "PROCESS_TASK")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProcessTask extends BaseEntity<Integer> {

    @Id
    @Column(name = "id")
    private Integer id;

    private Integer processId;
    private String name;
    private String description;
    private Integer minutesToComplete;
    private Integer minutesToAssign;
    @Column(name = "OrderBy")
    private Integer order;
    private String requestParam;
    private Integer taskId;
    private Integer defaultPriority;

}
