package com.iassure.incident.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

/**
 * 
 * @author Naveen kumar chintala
 *
 */

@Entity
@Table(name = "incident_case_type_master")
public class IncidentCaseTypeMaster {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pk_case_type_id")
	private int caseTypeId;
	
	
	@Column(name = "case_type_name")
	private String caseTypeName;
	
	@Column(name = "seq_order")
	private int sequnceOrder;
	
	@Column(name = "created_on")
	private Timestamp cratedDate;
	
	@Column(name = "created_by")
	private int createdBy;

	public int getCaseTypeId() {
		return caseTypeId;
	}

	public void setCaseTypeId(int caseTypeId) {
		this.caseTypeId = caseTypeId;
	}

	public String getCaseTypeName() {
		return caseTypeName;
	}

	public void setCaseTypeName(String caseTypeName) {
		this.caseTypeName = caseTypeName;
	}

	public int getSequnceOrder() {
		return sequnceOrder;
	}

	public void setSequnceOrder(int sequnceOrder) {
		this.sequnceOrder = sequnceOrder;
	}

	public Timestamp getCratedDate() {
		return cratedDate;
	}

	public void setCratedDate(Timestamp cratedDate) {
		this.cratedDate = cratedDate;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	@Override
	public String toString() {
		return "IncidentCaseTypeMaster [caseTypeId=" + caseTypeId + ", caseTypeName=" + caseTypeName + ", sequnceOrder="
				+ sequnceOrder + ", cratedDate=" + cratedDate + ", createdBy=" + createdBy + "]";
	}
	
	
	
	
	

}
