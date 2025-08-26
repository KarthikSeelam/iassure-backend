package com.iassure.incident.response;

import com.iassure.incident.entity.IncidentHistory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter@Setter@NoArgsConstructor@AllArgsConstructor
public class GetHistoryResponse {
    private StatusResponse statusResponse;
    private List<IncidentHistory> incidentHistories;
}
