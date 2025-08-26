package com.iassure.incident.repository;

import com.iassure.incident.dto.IncidentFieldsDTO;
import com.iassure.incident.entity.IncidentField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface IncidentFieldRepo extends JpaRepository<IncidentField,Integer> {

    @Query(value = "SELECT \n" +
            "    f.id AS fieldId," +
            "    i.id as incidentFieldId,\n" +
            "    JSON_UNQUOTE(JSON_EXTRACT(f.field_data, '$.type')) AS type,\n" +
            "    JSON_UNQUOTE(JSON_EXTRACT(f.field_data, '$.label')) AS label,\n" +
            "    JSON_EXTRACT(f.field_data, '$.options') AS options,\n" +
            "    i.selected_option AS selectedOption\n" +
            "FROM \n" +
            "    incident_fields i\n" +
            "JOIN \n" +
            "    fields f ON i.field_id = f.id\n" +
            "WHERE \n" +
            "    i.incident_id = :incidentId AND i.is_active = 1",nativeQuery = true)
    List<Object[]> findFieldsByIncidentId(@Param("incidentId") Integer incidentId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE incident_fields set is_active = 0 WHERE incident_id = :id and field_id = :fieldId",nativeQuery = true)
    void deleteIncidentField(int id,int fieldId);
}
