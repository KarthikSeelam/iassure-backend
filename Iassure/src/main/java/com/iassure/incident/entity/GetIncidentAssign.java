package com.iassure.incident.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter@Entity
public class GetIncidentAssign {
    @Id
    @Column(name = "AssigningID")
    private Integer assigningId;

    @Column(name = "IncidentID")
    private Integer incidentId;

    @Column(name = "AssignTypeID")
    private Integer assignTypeId;

    @Column(name = "AssignTo")
    private Integer assignTo;

    @Column(name = "CCUserID")
    private Integer ccUserId;

    @Column(name = "RedoFlag")
    private Integer redoFlag;

    @Column(name = "Comments")
    private String comments;

    @Column(name = "CreatedAt")
    private String createdAt;

    @Column(name = "UpdatedAt")
    private String updatedAt;

    @Column
    private String assignToName;

    @Column
    private String managerName;
}
