package com.iassure.incident.response;

import com.iassure.incident.entity.DocumentEntity;
import com.iassure.incident.entity.GetPreventiveActionDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter@Setter@NoArgsConstructor@AllArgsConstructor
public class GetPreventiveActionResponse {
    private StatusResponse statusResponse;
    private GetPreventiveActionDetails preventiveActionDetails;
    private List<DocumentEntity> preventiveActionFiles;
}
