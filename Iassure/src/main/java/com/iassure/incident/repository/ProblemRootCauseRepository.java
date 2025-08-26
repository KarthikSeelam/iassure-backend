package com.iassure.incident.repository;

import com.iassure.incident.entity.ProblemRootCause;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProblemRootCauseRepository extends JpaRepository<ProblemRootCause,Integer> {
    @Procedure(procedureName = "USP_GetProblemRootCause")
    List<ProblemRootCause> getProblemDetails(int orgId,int incidentId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM ProblemRootCause WHERE ProblemStageID = :problemStageId and IncidentID = :incidentId",nativeQuery = true)
    void deleteProblemRootCause(int problemStageId,int incidentId);
}
