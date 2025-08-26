package com.iassure.incident.repository;

import com.iassure.incident.entity.IncidentAssign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface IncidentAssignRepository extends JpaRepository<IncidentAssign,Integer> {

    @Procedure(procedureName = "USP_InsIncidentAssign")
    IncidentAssign saveIncidentAssign(String flag, int assigningId, int incidentId, int assignTypeId, int assignTo, int ccId, int redoFlag, String comments);

    @Modifying
    @Transactional
    @Procedure(procedureName = "USP_IncidentStatusChange")
    void changeToInProgress(int incidentId);
}
