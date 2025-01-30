package com.ninjacart.task_mgmt_service.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "TEAM")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Team extends BaseEntityIntID{
    @Column(name = "teamName")
    private String teamName;
    @Column(name = "teamDescription")
    private String teamDescription;
}
