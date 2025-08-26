package com.iassure.incident.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

/**
 * 
 * @author Naveen kumar chintala
 *
 */
@Entity
@Table(name = "incident_corrective_action_plan_master")
public class CorrectiveActionPlanMaster {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pk_cap_id")
	private int pkCapId;
	
	@Column(name = "cap_name")
	private String capName;
	
	@Column(name = "created_on")
	private Timestamp createdOn;
	
	@Column(name = "created_by")
	private int createdBy;
	
	@Column(name = "updated_on")
	private Timestamp updatedOn;
	
	@Column(name = "updated_by")
	private int updatedBy;

	public int getPkCapId() {
		return pkCapId;
	}

	public void setPkCapId(int pkCapId) {
		this.pkCapId = pkCapId;
	}

	public String getCapName() {
		return capName;
	}

	public void setCapName(String capName) {
		this.capName = capName;
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

	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}

	public int getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Override
	public String toString() {
		return "CorrectiveActionPlanMaster [pkCapId=" + pkCapId + ", capName=" + capName + ", createdOn=" + createdOn
				+ ", createdBy=" + createdBy + ", updatedOn=" + updatedOn + ", updatedBy=" + updatedBy + "]";
	}
	
	

}
