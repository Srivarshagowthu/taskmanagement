package com.product.borg.persistance.entity;

import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    private String filter;

}
