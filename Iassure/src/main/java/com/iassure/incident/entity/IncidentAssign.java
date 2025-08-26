package com.iassure.incident.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@Entity
@Table(name = "IncidentAssign")
public class IncidentAssign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AssigningID")
    private Integer assigningID;

    @Column(name = "IncidentID")
    private Integer incidentID;

    @Column(name = "AssignTypeID")
    private Integer assignTypeID;

    @Column(name = "AssignTo")
    private Integer assignTo;

    @Column(name = "CCUserID")
    private Integer ccUserID;

    @Column(name = "RedoFlag")
    private Integer redoFlag;

    @Column(name = "Comments")
    private String comments;

    @Column(name = "IncidentStatusID")
    private Integer incidentStatusID;

    @Column(name = "InterimComments")
    private String interimComments;

    @Column(name = "CreatedAt")
    private Date createdAt;

    @Column(name = "UpdatedAt")
    private Date updatedAt;
}
