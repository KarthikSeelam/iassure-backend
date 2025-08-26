package com.iassure.incident.repository;

import com.iassure.incident.entity.IncidentHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncidentHistoryRepo extends JpaRepository<IncidentHistory,Integer> {
    @Procedure(procedureName = "USP_GetIncidentHistory")
    List<IncidentHistory> getIncidentHistory(int incidentId);

    @Procedure(procedureName = "USP_InsIncidentHistory")
    void saveIncidentHistory(int incidentId,String method,String comments,String createdBy);


}
