package com.iassure.incident.repository;

import com.iassure.incident.entity.IncidentDetailsById;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IncidentDetailsByIdRepo extends JpaRepository<IncidentDetailsById,Integer> {
    @Procedure(procedureName = "USP_GetIncidentDetails")
    IncidentDetailsById getIncidentDetailsById(@Param("piOrgID") int orgId,@Param("piIncidentID") int incidentId,@Param("piUserID") int userId);
}
