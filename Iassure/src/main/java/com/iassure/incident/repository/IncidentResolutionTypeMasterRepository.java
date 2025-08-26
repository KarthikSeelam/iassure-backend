package com.iassure.incident.repository;

import com.iassure.incident.entity.IncidentResolutionTypeMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IncidentResolutionTypeMasterRepository extends JpaRepository<IncidentResolutionTypeMaster,Integer>{

    IncidentResolutionTypeMaster findByResolutionTypeNameIgnoreCase(String resolutionTypeName);
    @Query(value = "select COALESCE(max(seq_order+1),0) from incident_resolution_type_master" , nativeQuery = true)
    int getMaxSequenceId();
}