package com.iassure.incident.response;

import com.iassure.incident.entity.DocumentEntity;
import com.iassure.incident.entity.IncidentRootCause;
import com.iassure.incident.entity.ProblemRootCause;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RootCauseResponse {
    private StatusResponse statusResponse;
    private IncidentRootCause incidentRootCauseDetails;
    private List<ProblemRootCause> rootCauseDetails;
    private List<DocumentEntity> rootCauseFiles;
}
