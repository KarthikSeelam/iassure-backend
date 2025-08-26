package com.iassure.incident.repository;

import com.iassure.incident.entity.GetPreventiveActionDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

@Repository
public interface GetPreventiveActionRepo extends JpaRepository<GetPreventiveActionDetails,Integer> {
    @Procedure(procedureName = "USP_GetPreventiveActionDetails")
    GetPreventiveActionDetails getPreventiveActionDetails(int orgId,int incidentId,int userId);

}
