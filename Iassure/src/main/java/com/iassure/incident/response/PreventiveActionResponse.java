package com.iassure.incident.response;

import com.iassure.incident.entity.DocumentEntity;
import com.iassure.incident.entity.IncidentPreventiveAction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor@NoArgsConstructor
public class PreventiveActionResponse {
    private StatusResponse statusResponse;
    private IncidentPreventiveAction preventiveActionDetails;
    private List<DocumentEntity> preventiveFileDetails;
}
