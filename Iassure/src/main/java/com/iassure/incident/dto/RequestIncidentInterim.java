package com.iassure.incident.dto;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class RequestIncidentInterim {
    private Integer interimId;
    private Integer incidentId;
    private String docFlag;
    private String flag;
    private Integer createdBy;
    private Integer userId;
    private String findings;
}
