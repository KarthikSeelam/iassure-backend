package com.iassure.incident.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter@Entity
public class IncidentHistory {
    @Id
    @Column(name = "IncidentHistoryID")
    private Integer incidentHistoryId;

    @Column(name = "IncidentID")
    private Integer incidentId;

    @Column(name = "Method")
    private String method;

    @Column(name = "Comments")
    private String comments;

    @Column(name = "CreatedBy")
    private String createdBy;

    @Column(name = "IsActive")
    private Integer isActive;

    @Column(name = "CreatedAt")
    private String createdAt;
}
