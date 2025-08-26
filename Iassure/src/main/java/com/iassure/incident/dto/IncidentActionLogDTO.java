/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iassure.incident.dto;


import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author Jyothi.T
 */
public class IncidentActionLogDTO implements Serializable{
    
    private Integer incidentActionLogId;
    private IncidentsDTO incidentDetails;
    private String caseType;
    private String investigation_findings;
    private String corrective_actions;
    private String preventative_actions;

    private Timestamp createdOn;

	private int incidentSpecificToJobFlag;
	private Integer fkJobSchedulingId;
	private Integer incidentResolvedFlag;
	
	

    public Integer getIncidentActionLogId() {
        return incidentActionLogId;
    }

    public void setIncidentActionLogId(Integer incidentActionLogId) {
        this.incidentActionLogId = incidentActionLogId;
    }

    public IncidentsDTO getIncidentDetails() {
        return incidentDetails;
    }

    public void setIncidentDetails(IncidentsDTO incidentDetails) {
        this.incidentDetails = incidentDetails;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public String getInvestigation_findings() {
        return investigation_findings;
    }

    public void setInvestigation_findings(String investigation_findings) {
        this.investigation_findings = investigation_findings;
    }

    public String getCorrective_actions() {
        return corrective_actions;
    }

    public void setCorrective_actions(String corrective_actions) {
        this.corrective_actions = corrective_actions;
    }

    public String getPreventative_actions() {
        return preventative_actions;
    }

    public void setPreventative_actions(String preventative_actions) {
        this.preventative_actions = preventative_actions;
    }

    /*public StatusDTO getStatusDetails() {
        return statusDetails;
    }

    public void setStatusDetails(StatusDTO statusDetails) {
        this.statusDetails = statusDetails;
    }*/

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    /*public UserDTO getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserDTO createdBy) {
        this.createdBy = createdBy;
    }*/

	public int getIncidentSpecificToJobFlag() {
		return incidentSpecificToJobFlag;
	}

	public void setIncidentSpecificToJobFlag(int incidentSpecificToJobFlag) {
		this.incidentSpecificToJobFlag = incidentSpecificToJobFlag;
	}

	public Integer getFkJobSchedulingId() {
		return fkJobSchedulingId;
	}

	public void setFkJobSchedulingId(Integer fkJobSchedulingId) {
		this.fkJobSchedulingId = fkJobSchedulingId;
	}

	public Integer getIncidentResolvedFlag() {
		return incidentResolvedFlag;
	}

	public void setIncidentResolvedFlag(Integer incidentResolvedFlag) {
		this.incidentResolvedFlag = incidentResolvedFlag;
	}
    
    
    
    
    
    
    
}
