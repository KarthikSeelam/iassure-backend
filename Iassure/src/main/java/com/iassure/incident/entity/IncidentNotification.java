package com.iassure.incident.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter@Entity
public class IncidentNotification {
    @Id
    @Column(name = "IncidentHistoryID")
    private Integer incidentHistoryId;

    @Column(name = "IncidentRecord")
    private String incidentRecord;

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
