package com.iassure.incident.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;
/**
 * 
 * @author Naveen kumar chintala
 *
 */
@Entity
@Table(name = "incident_resolution_type_master")
public class IncidentResolutionTypeMaster {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pk_resolution_type_id")
	private int resolutionTypeId;

	@Column(name = "resolution_type_name")
	private String resolutionTypeName;
	
	@Column(name = "seq_order")
	private Integer sequenceOrder;
	
	@Column(name = "created_on")
	private Timestamp createdTime;
	
	@Column(name = "created_by")
	private int createdBy;

	public int getResolutionTypeId() {
		return resolutionTypeId;
	}

	public void setResolutionTypeId(int resolutionTypeId) {
		this.resolutionTypeId = resolutionTypeId;
	}

	
	public String getResolutionTypeName() {
		return resolutionTypeName;
	}

	public void setResolutionTypeName(String resolutionTypeName) {
		this.resolutionTypeName = resolutionTypeName;
	}

	public Integer getSequenceOrder() {
		return sequenceOrder;
	}

	public void setSequenceOrder(Integer sequenceOrder) {
		this.sequenceOrder = sequenceOrder;
	}

	public Timestamp getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	@Override
	public String toString() {
		return "IncidentResolutionTypeMaster [resolutionTypeId=" + resolutionTypeId + ", resolutionTypeName="
				+ resolutionTypeName + ", sequenceOrder=" + sequenceOrder + ", createdTime=" + createdTime
				+ ", createdBy=" + createdBy + "]";
	}
	
	
	
	
	
	
}
