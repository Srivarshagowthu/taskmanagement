package com.ninjacart.task_mgmt_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "TEAM_PROCESS_TASK")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TeamProcessTask extends BaseEntityIntID{
    @Column(name = "ProcessTaskId")
    private Integer processTaskId;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TeamId")
    private Team team;
    @Column(name= "TicketType")
    private String ticketType;
    @Column(name = "TicketTypeId")
    private Integer ticketTypeId;
    @Column(name = "Priority")
    private String priority;
    @Column(name = "RefType")
    private String refType;
    @Column(name = "RefId")
    private Integer refId;
    @Column(name = "CacheKey")
    private String cacheKey;

}
