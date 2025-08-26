package com.iassure.incident.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestBodyDTO {
    private int orgId;
    private int incidentStatusId;
    private int incidentId;
    private int userId;
    private String flag;
    private int departmentId;
    private String sourceName;
}
