package com.iassure.incident.response;

import com.iassure.incident.entity.DocumentEntity;
import com.iassure.incident.entity.IncidentInterim;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor
public class SaveInterimResponse {
    private StatusResponse statusResponse;
    private IncidentInterim incidentInterimDetails;
    private List<DocumentEntity> interimFiles;
}
