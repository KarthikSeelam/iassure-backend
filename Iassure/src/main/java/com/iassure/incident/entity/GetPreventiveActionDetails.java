package com.iassure.incident.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter@Setter@Entity
public class GetPreventiveActionDetails {

    @Id
    @Column(name = "IncidentID")
    private Integer incidentId;
    @Column(name = "OrgID")
    private Integer orgId;
    @Column(name = "PreventiveID")
    private Integer preventiveId;
    @Column(name = "Findings")
    private String findings;
    @Column(name = "CreatedAt")
    private Date createdAt;
    @Column(name = "CreatedBy")
    private Integer createdBy;
}
