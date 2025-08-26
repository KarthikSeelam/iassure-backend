package com.iassure.incident.repository;

import com.iassure.incident.entity.IncidentChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncidentChatRepository extends JpaRepository<IncidentChat,Integer> {
}
