package com.iassure.incident.repository;

import com.iassure.incident.entity.IncidentRootCause;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface IncidentRootCauseRepository extends JpaRepository<IncidentRootCause,Integer> {

    @Procedure(procedureName = "USP_InsUpdDelIncidentRootCause")
    IncidentRootCause saveIncidentRootCause(String flag, int rootCauseId, int incidentId, String problemDescription, String problemWhy, String summary, int createdBy);

}
