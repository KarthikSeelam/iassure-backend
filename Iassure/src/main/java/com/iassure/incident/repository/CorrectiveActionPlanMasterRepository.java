package com.iassure.incident.repository;

import com.iassure.incident.entity.IncidentCorrectiveActionPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

@Repository
public interface CorrectiveActionPlanMasterRepository extends JpaRepository<IncidentCorrectiveActionPlan,Integer> {
    @Procedure(procedureName = "USP_GetCorrectiveActionDetails")
    IncidentCorrectiveActionPlan getCorrectiveActionDetails(int orgId,int incidentId,int userId);

}