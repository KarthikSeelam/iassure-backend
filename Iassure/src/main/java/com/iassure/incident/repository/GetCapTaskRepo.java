package com.iassure.incident.repository;

import com.iassure.incident.entity.GetCapTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GetCapTaskRepo extends JpaRepository<GetCapTask,Integer> {
    @Procedure(procedureName = "USP_GetCAPTask")
    List<GetCapTask> getCapTaskDetails(int orgId, int incidentId);
}
