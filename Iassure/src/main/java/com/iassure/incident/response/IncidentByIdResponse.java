package com.iassure.incident.response;

import com.iassure.incident.entity.DocumentEntity;
import com.iassure.incident.entity.IncidentDetailsById;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter@NoArgsConstructor@AllArgsConstructor
public class IncidentByIdResponse {
    private StatusResponse statusResponse;
    private IncidentDetailsById incidentDetails;
    private String summary;
    private List<DocumentEntity> incidentFiles;
}
