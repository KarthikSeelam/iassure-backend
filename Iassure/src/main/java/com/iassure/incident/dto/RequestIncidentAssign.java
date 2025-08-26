package com.iassure.incident.dto;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class RequestIncidentAssign {
    private String flag;
    private int incidentId;
    private int assigningId;
    private int assignTypeId;
    private int assignTo;
    private int managerId;
    private int redoFlag;
    private String comments;
}
