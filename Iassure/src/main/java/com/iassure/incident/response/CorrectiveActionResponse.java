package com.iassure.incident.response;

import com.iassure.incident.dto.CorrectiveActionPlan;
import com.iassure.incident.entity.CAPTaskMaster;
import com.iassure.incident.entity.DocumentEntity;
import com.iassure.incident.entity.IncidentCorrectiveActionPlan;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CorrectiveActionResponse {
    private StatusResponse statusResponse;
    private IncidentCorrectiveActionPlan incidentCorrectiveActionPlanDetails;
    private List<DocumentEntity> correctiveFiles;
}
