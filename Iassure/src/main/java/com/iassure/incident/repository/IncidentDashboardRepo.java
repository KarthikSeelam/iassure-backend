package com.iassure.incident.repository;

import com.iassure.incident.entity.IncidentDashboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncidentDashboardRepo extends JpaRepository<IncidentDashboard,Integer> {
    @Procedure(procedureName = "USP_GetIncidentDetails_Dashboard")
    List<IncidentDashboard> getIncidentDetailsForDashboard(int orgId, int incidentStatusId,int assignedUserId);
}
