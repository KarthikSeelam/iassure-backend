package com.iassure.incident.response;

import com.iassure.incident.entity.IncidentCount;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IncidentCountResponse {
    private StatusResponse statusResponse;
    private IncidentCount incidentCount;
}
