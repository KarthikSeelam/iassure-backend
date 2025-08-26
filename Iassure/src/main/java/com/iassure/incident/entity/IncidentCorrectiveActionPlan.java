package com.iassure.incident.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Naveen kumar chintala
 */
@Entity
@Table(name = "IncidentCAP")
@Getter
@Setter
public class IncidentCorrectiveActionPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CAPID")
    private Integer incidentActionPlanId;

    @Column(name = "IncidentID")
    private Integer incidentId;

    @Column(name = "Comments")
    private String comments;

    @Column(name = "CreatedAt")
    private Timestamp createdOn;

    @Column(name = "CreatedBy")
    private Integer createdBy;


}
