package com.iassure.incident.repository;

import com.iassure.incident.entity.Incidents;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface IncidentDetailsRepository extends JpaRepository<Incidents,Integer> {

    @Procedure(procedureName = "USP_CloseIncident")
    void closeIncident(int incidentId,int userId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE IncidentDetails \n" +
            "SET IncidentRecord = CONCAT(\n" +
            "    (SELECT UPPER(SUBSTRING(SourceType, 1, 3)) \n" +
            "     FROM MasterSource \n" +
            "     WHERE SourceID = :sourceId),\n" +
            "    '_', \n" +
            "    :incidentId\n" +
            ")\n" +
            "WHERE IncidentId = :incidentId", nativeQuery = true)
    void updateUniqueIncidentRecord(@Param("sourceId") int sourceId, @Param("incidentId") int incidentId);

}