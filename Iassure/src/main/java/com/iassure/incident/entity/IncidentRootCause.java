package com.iassure.incident.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
public class IncidentRootCause {

    @Id
    @Column(name = "RootCauseID")
    private Integer rootCauseId;

    @Column(name = "IncidentID")
    private Integer incidentId;

    @Column(name = "ProblemDescription")
    private String problemDescription;

    @Column(name = "Problemwhy")
    private String problemWhy;

    @Column(name = "RootCauseSummary")
    private String rootCauseSummary;

    @Column(name = "CreatedBy")
    private Integer createdBy;

    @Column(name = "CreatedAt")
    private String createdAt;
}
