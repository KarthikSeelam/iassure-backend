package com.iassure.incident.repository;

import com.iassure.incident.entity.IncidentHistory;
import com.iassure.incident.entity.IncidentNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncidentNotificationRepo extends JpaRepository<IncidentNotification,Integer> {
    @Procedure(procedureName = "USP_GetAllNotifications")
    List<IncidentNotification> getAllNotifications();
}
