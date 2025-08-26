package com.iassure.incident.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter@Entity
public class GetCapTask {
    @Id
    @Column(name = "CAPTaskID")
    private Integer capTaskId;

    @Column(name = "IncidentID")
    private Integer incidentID;
    @Column(name = "TaskID")
    private Integer taskID;
    @Column(name = "SourceType")
    private String taskName;
    @Column(name = "DueDate")
    private String dueDate;
    @Column(name = "ResolvedFlag")
    private Integer resolvedFlag;
    @Column(name = "CreatedBy")
    private Integer createdBy;
    @Column(name = "CreatedAt")
    private String createdAt;
}
