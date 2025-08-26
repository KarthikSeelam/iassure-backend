package com.iassure.incident.repository;

import com.iassure.incident.entity.IncidentPreventiveAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

@Repository
public interface PreventiveActionRepository extends JpaRepository<IncidentPreventiveAction,Integer> {

    @Procedure(procedureName = "USP_InsUpdDelIncidentPreventiveAction")
    IncidentPreventiveAction savePreventiveAction(String flag, int preventiveId,int incidentId, String findings, int createdBy);
}
