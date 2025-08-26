package com.iassure.incident.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CorrectiveActionPlan {
    private Integer incidentActionPlanId;
    private String flag;
    private Integer incidentId;
    private Integer userId;
    private Integer orgId;
    private String comments;
}
