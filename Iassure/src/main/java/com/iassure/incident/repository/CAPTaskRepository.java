package com.iassure.incident.repository;

import com.iassure.incident.entity.CAPTaskMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CAPTaskRepository extends JpaRepository<CAPTaskMaster,Integer> {
    @Procedure(procedureName = "USP_InsUpdDelCAPTask")
    CAPTaskMaster saveCapTasks(String flag,int capTaskId,int incidentId,int taskId,String comments,String dueDate,int resolvedFlag,int createdBy);
}
