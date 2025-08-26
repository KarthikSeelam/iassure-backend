package com.iassure.incident.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "ProblemRootCause")
public class ProblemRootCause {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProblemStageID")
    private Integer problemStageId;

    @Column(name = "IncidentID")
    private Integer incidentId;

    @Column(name = "Stage")
    private String stage;

    @Column(name = "StageNumber")
    private String stageNumber;

    @Column(name = "Occur")
    private String occur;

    @Column(name = "Undetected")
    private String undetected;

    @Column(name = "Prevented")
    private String prevented;

    @Column(name = "CreatedBy")
    private Integer createdBy;

    @Column(name = "CreatedAt")
    private Timestamp createdAt;
}
