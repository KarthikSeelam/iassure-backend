package com.iassure.incident.repository;

import com.iassure.incident.entity.NotificationUserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationUserDetailsRepository extends JpaRepository<NotificationUserDetails,Integer> {

}