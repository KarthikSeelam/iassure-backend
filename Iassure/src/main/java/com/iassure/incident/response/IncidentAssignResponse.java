package com.iassure.incident.response;

import com.iassure.incident.entity.IncidentAssign;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter@AllArgsConstructor@NoArgsConstructor
public class IncidentAssignResponse {
    private StatusResponse statusResponse;
    private IncidentAssign incidentAssign;
}
