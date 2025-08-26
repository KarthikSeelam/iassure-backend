package com.iassure.incident.response;

import com.iassure.incident.entity.GetIncidentAssign;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor
public class GetIncidentAssignResponse {
    private StatusResponse statusResponse;
    private GetIncidentAssign incidentAssignDetails;
}
