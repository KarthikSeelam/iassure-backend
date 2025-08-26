package com.iassure.incident.repository;

import com.iassure.incident.entity.GetIncidentRootCause;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

@Repository
public interface GetIncidentRootCauseRepo extends JpaRepository<GetIncidentRootCause,Integer> {
    @Procedure(procedureName = "USP_GetRootCauseAnalysisDetails")
    GetIncidentRootCause getRootCauseDetails(int orgId,int incidentId,int userId);
}
