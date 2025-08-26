package com.iassure.incident.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter@Setter@Entity
public class IncidentDetailsById {
    @Id
    @Column(name = "IncidentID")
    private Integer incidentId;

    @Column(name = "OrgID")
    private Integer orgId;

    @Column(name = "SourceID")
    private Integer sourceId;

    @Column(name = "CategoryID")
    private Integer categoryId;

    @Column(name = "CaseSummary")
    private String title;

    @Column(name = "CaseDescription")
    private String description;

    @Column(name = "SeverityID")
    private Integer severityId;

    @Column(name = "AttachmentURL")
    private String attachmentUrl;

    @Column(name = "Comments")
    private String comments;

    @Column(name = "AssignedUserID")
    private Integer assignedUserId;

    @Column(name = "IncidentStatusID")
    private Integer incidentStatusId;

    @Column(name = "CreatedBy")
    private Integer createdBy;

    @Column(name = "OrganizationName")
    private String organizationName;

    @Column(name = "Category")
    private String category;

    @Column(name = "Severity")
    private String severity;

    @Column(name = "CreatedAt")
    private Date createdAt;

    @Column(name = "Status")
    private String status;

    @Column(name = "Source")
    private String source;

    @Column(name = "AssignedUser")
    private String assignedUser;

    @Column(name = "DepartmentName")
    private String departmentName;

    @Column(name = "DepartmentID")
    private Integer departmentId;
    @Override
    public String toString() {
        return "IncidentDetailsById{" +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", comments='" + comments + '\'' +
                ", organizationName='" + organizationName + '\'' +
                ", category='" + category + '\'' +
                ", severity='" + severity + '\'' +
                ", createdAt=" + createdAt +
                ", status='" + status + '\'' +
                ", source='" + source + '\'' +
                ", assignedUser='" + assignedUser + '\'' +
                ", departmentName='" + departmentName + '\''+
                '}';
    }
}
