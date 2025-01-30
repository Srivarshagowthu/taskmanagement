package com.ninjacart.task_mgmt_service.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "PROCESS")
public class Process extends BaseEntity<Integer> {



    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "Description")
    private String Description;

    @Column(name = "Version")
    private Integer version;

    @Column(name = "Domain")
    private String domain;

    @Column(name = "Active")
    private Integer active;

    @Column(name = "ReferenceType")
    private String referenceType;

    @Column(name = "ReferenceValue")
    private String referenceValue;

}