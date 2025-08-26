package com.iassure.incident.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "CAPTask")
public class CAPTaskMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CAPTaskID")
    private Integer capTaskId;

    @Column(name = "IncidentID")
    private Integer incidentId;
    @Column(name = "TaskID")
    private Integer taskId;
    @Column(name = "DueDate")
    private String dueDate;
    @Column(name = "ResolvedFlag")
    private Integer resolvedFlag;
    @Column(name = "CreatedBy")
    private Integer createdBy;
    @Column(name = "CreatedAt")
    private String createdAt;

}
