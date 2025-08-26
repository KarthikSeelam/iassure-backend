package com.iassure.incident.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter@Setter@Entity
public class GetIncidentRootCause {
    @Id
    @Column(name = "RootCauseID")
    private Integer rootCauseId;

    @Column(name = "IncidentID")
    private Integer incidentId;

    @Column(name = "OrgID")
    private Integer orgId;

    @Column(name = "ProblemDescription")
    private String problemDescription;

    @Column(name = "Problemwhy")
    private String problemWhy;

    @Column(name = "RootCauseSummary")
    private String rootCauseSummary;

    @Column(name = "CreatedBy")
    private Integer createdBy;

    @Column(name = "CreatedAt")
    private Timestamp createdAt;
}
