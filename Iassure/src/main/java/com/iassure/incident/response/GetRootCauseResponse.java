package com.iassure.incident.response;

import com.iassure.incident.dto.RootCauseWhy;
import com.iassure.incident.entity.DocumentEntity;
import com.iassure.incident.entity.GetIncidentRootCause;
import com.iassure.incident.entity.ProblemRootCause;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor
public class GetRootCauseResponse {
    private StatusResponse statusResponse;
    private GetIncidentRootCause incidentRootCauseDetails;
    private List<ProblemRootCause> rootCauseDetails;
    private List<DocumentEntity> rootCauseFiles;
}
