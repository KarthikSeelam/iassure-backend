package com.iassure.incident.repository;

import com.iassure.incident.entity.IncidentCaseTypeMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IncidentCaseTypeMasterRepository extends JpaRepository<IncidentCaseTypeMaster,Integer> {

    IncidentCaseTypeMaster findByCaseTypeNameIgnoreCase(String caseTypeName);
    @Query(value = "select COALESCE(max(seq_order+1), 0) from incident_case_type_master" , nativeQuery = true)
    int getMaxSequenceId();
}