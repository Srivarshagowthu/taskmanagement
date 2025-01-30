package com.ninjacart.task_mgmt_service.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Variable extends BaseEntityIntID {

    private String name;

}
