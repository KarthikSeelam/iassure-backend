package com.iassure.incident.repository;

import com.iassure.incident.entity.GetIncidentAssign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

@Repository
public interface GetIncidentAssignRepository extends JpaRepository<GetIncidentAssign,Integer> {
    @Procedure(procedureName = "USP_GetIncidentAssignDetails")
    GetIncidentAssign getAssign(int incidentId);
}
