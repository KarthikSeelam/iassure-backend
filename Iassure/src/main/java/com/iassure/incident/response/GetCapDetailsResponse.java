package com.iassure.incident.response;

import com.iassure.incident.entity.DocumentEntity;
import com.iassure.incident.entity.GetCapTask;
import com.iassure.incident.entity.IncidentCorrectiveActionPlan;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter@Setter@NoArgsConstructor@AllArgsConstructor
public class GetCapDetailsResponse {
    private StatusResponse statusResponse;
    private IncidentCorrectiveActionPlan incidentCorrectiveActionPlanDetails;
    private List<DocumentEntity> capFileDetails;
}
