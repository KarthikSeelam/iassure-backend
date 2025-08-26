package com.iassure.incident.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter@Setter@Entity@Table(name = "InterimInvestigation")
public class IncidentInterim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "InterimID")
    private Integer interimId;

    @Column(name = "IncidentID")
    private Integer incidentId;

    @Column(name = "Findings")
    private String findings;

    @Column(name = "CreatedBy")
    private Integer createdBy;

    @Column(name = "CreatedAt")
    private Timestamp createdAt;
}
