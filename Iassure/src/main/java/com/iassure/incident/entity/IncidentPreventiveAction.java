package com.iassure.incident.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Entity
@Table(name = "IncidentPreventiveAction")
@Getter
@Setter
public class IncidentPreventiveAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PreventiveID")
    private Integer preventiveId;

    @Column(name = "IncidentID")
    private Integer incidentId;

    @Column(name = "Findings")
    private String findings;

    @Column(name = "History")
    private String history;

    @Column(name = "CreatedBy")
    private Integer createdBy;

    @Column(name = "CreatedAt")
    private String createdAt;
}