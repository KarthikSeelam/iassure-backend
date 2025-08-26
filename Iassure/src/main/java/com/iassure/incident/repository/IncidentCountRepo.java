package com.iassure.incident.repository;

import com.iassure.incident.entity.IncidentCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

@Repository
public interface IncidentCountRepo extends JpaRepository<IncidentCount,Integer> {
    @Procedure(procedureName = "USP_GetIncidentCount_Dashboard")
    IncidentCount getIncidentCount(Integer orgId,Integer userId);
}
