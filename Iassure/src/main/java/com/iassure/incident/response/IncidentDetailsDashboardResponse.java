package com.iassure.incident.response;

import com.iassure.incident.entity.IncidentDashboard;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IncidentDetailsDashboardResponse {
    private StatusResponse statusResponse;
    private Integer totalRecords;
    private List<IncidentDashboard> dashboardList;
}
