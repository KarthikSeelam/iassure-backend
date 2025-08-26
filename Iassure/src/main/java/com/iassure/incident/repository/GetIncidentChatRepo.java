package com.iassure.incident.repository;

import com.iassure.incident.entity.GetIncidentChatDetails;
import com.iassure.incident.entity.IncidentChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GetIncidentChatRepo extends JpaRepository<GetIncidentChatDetails,Integer> {
    @Procedure(procedureName = "USP_GetIncidentChatDetails")
    List<GetIncidentChatDetails> getIncidentChatByIncidentId(int incidentId);
}
