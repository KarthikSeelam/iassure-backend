package com.iassure.incident.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * 
 * @author Naveen kumar chintala
 *
 */
@Entity
@Table(name = "IncidentDetails")
@Getter
@Setter
public class Incidents {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IncidentID")
	private Integer incidentId;
	@Column(name = "IncidentRecord")
	private String incidentRecord;
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
	@Column(name = "ProductName")
	private String productName;
	@Column(name = "IssueType")
	private String issueType;
	@Column(name = "SupplierName")
	private String supplierName;
	@Column(name = "AffectedQuantity")
	private Integer affectedQuantity;
	@Column(name = "IssueArea")
	private String issueArea;
	@Column(name = "ProductCode")
	private String productCode;
	@Column(name = "BatchNo")
	private String batchNo;
	@Column(name = "Comments")
	private String comments;
	@Column(name = "AssignedUserID")
	private Integer assignedUserId;
	@Column(name = "IncidentStatusID")
	private Integer incidentStatusId;
	@Column(name = "CreatedBy")
	private Integer createdBy;
	@Column(name = "CreatedAt")
	private Timestamp createdAt;
	@Column(name = "UpdatedAt")
	private Timestamp updatedAt;
	@Column(name = "DepartmentID")
	private Integer departmentId;

}
