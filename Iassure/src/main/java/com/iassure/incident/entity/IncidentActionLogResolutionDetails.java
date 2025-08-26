package com.iassure.incident.entity;

import jakarta.persistence.*;
import java.sql.Timestamp;
/**
 * 
 * @author Naveen kumar chintala
 *
 */
@Entity
@Table(name = "incident_resolution_details")
public class IncidentActionLogResolutionDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pk_incident_resolution_id")
	private int incidentResolutionId;
	
	@Column(name = "resolution_id")
	private int resolutionId;
	
	@Column(name = "created_on")
	private Timestamp createdOn;
	
	@Column(name  = "created_by")
	private int createdBy;

	@Column(name = "fk_incident_id")
	private int incidentId;
	
	public int getIncidentResolutionId() {
		return incidentResolutionId;
	}

	public void setIncidentResolutionId(int incidentResolutionId) {
		this.incidentResolutionId = incidentResolutionId;
	}

	public int getResolutionId() {
		return resolutionId;
	}

	public void setResolutionId(int resolutionId) {
		this.resolutionId = resolutionId;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public int getIncidentId() {
		return incidentId;
	}

	public void setIncidentId(int incidentId) {
		this.incidentId = incidentId;
	}

	@Override
	public String toString() {
		return "IncidentActionLogResolutionDetails [incidentResolutionId=" + incidentResolutionId + ", resolutionId="
				+ resolutionId + ", createdOn=" + createdOn + ", createdBy=" + createdBy + ", incidentId=" + incidentId
				+ "]";
	}
	

		
	
	
}
