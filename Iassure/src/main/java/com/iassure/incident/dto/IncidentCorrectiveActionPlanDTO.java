/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iassure.incident.dto;



import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author Jyothi.T
 */
public class IncidentCorrectiveActionPlanDTO implements Serializable{
    
    private Integer incidentActionPlanId;
    private IncidentActionLogDTO actionLogDetails;
    private String task;
    private String actionTaken;

    private Date actionDate;
    private int correctiveActionResolved;

    public Integer getIncidentActionPlanId() {
        return incidentActionPlanId;
    }

    public void setIncidentActionPlanId(Integer incidentActionPlanId) {
        this.incidentActionPlanId = incidentActionPlanId;
    }

    public IncidentActionLogDTO getActionLogDetails() {
        return actionLogDetails;
    }

    public void setActionLogDetails(IncidentActionLogDTO actionLogDetails) {
        this.actionLogDetails = actionLogDetails;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getActionTaken() {
        return actionTaken;
    }

    public void setActionTaken(String actionTaken) {
        this.actionTaken = actionTaken;
    }


    public Date getActionDate() {
        return actionDate;
    }

    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }

	public int getCorrectiveActionResolved() {
		return correctiveActionResolved;
	}

	public void setCorrectiveActionResolved(int correctiveActionResolved) {
		this.correctiveActionResolved = correctiveActionResolved;
	}
    
    
    
    
    
}
