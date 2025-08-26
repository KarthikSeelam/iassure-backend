package com.iassure.incident.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class IncidentDashboard {
    @Id
    @Column(name = "IncidentID")
    private String incidentId;
    @Column(name = "IncidentRecord")
    private String incidentRecord;
    @Column(name = "DepartmentName")
    private String departmentName;
    @Column(name = "Subject")
    private String subject;
    @Column(name = "OrganizationName")
    private String organizationName;
    @Column(name = "Category")
    private String category;
    @Column(name = "Severity")
    private String severity;
    @Column(name = "Status")
    private String status;
    @Column(name = "Created On")
    private String createdOn;




}
