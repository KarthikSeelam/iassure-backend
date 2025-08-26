package com.iassure.incident.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PreventiveActionDTO {
    private Integer orgId;
    private Integer userId;
    private Integer preventiveId;
    private String flag;
    private Integer incidentId;
    private String findings;
    private int createdBy;
}
