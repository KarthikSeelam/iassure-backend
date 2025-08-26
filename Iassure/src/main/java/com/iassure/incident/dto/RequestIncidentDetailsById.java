package com.iassure.incident.dto;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class RequestIncidentDetailsById {
    private Integer incidentId;
    private Integer entityId;
    private Integer orgId;
    private Integer userId;

}
