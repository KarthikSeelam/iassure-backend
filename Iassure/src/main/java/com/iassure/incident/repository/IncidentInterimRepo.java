package com.iassure.incident.repository;

import com.iassure.incident.entity.IncidentInterim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

@Repository
public interface IncidentInterimRepo extends JpaRepository<IncidentInterim,Integer> {

    @Procedure(procedureName = "USP_GetInterimInvestigationDetails")
    IncidentInterim getInterimDetails(int orgId,int incidentId,int userId);

    @Procedure(procedureName = "USP_InsUpdDelInterimInvestigation")
    IncidentInterim saveInterimDetails(String flag, int interimId,int incidentId,String findings,int createdBy);
}
