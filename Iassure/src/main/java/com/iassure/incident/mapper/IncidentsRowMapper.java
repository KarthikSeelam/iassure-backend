package com.iassure.incident.mapper;

import com.iassure.incident.dto.IncidentsDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Jyothi.T
 */
public class IncidentsRowMapper implements RowMapper<IncidentsDTO>{
    @Override
    public IncidentsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return null;
    }
    

    /*public IncidentsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        IncidentsDTO dtoObj = new IncidentsDTO();
        
        dtoObj.setIncidentId(rs.getInt("pk_incident_id"));
        dtoObj.setSubject(rs.getString("subject"));        
        dtoObj.setDescription(rs.getString("description"));
        dtoObj.setSeverity(rs.getInt("severity"));
        dtoObj.setCategory(rs.getString("category"));
        dtoObj.setCreatedOn(rs.getTimestamp("created_on"));
        UserDetailsDTO createdByUser = new UserDetailsDTO();
        //createdByUser.setU(rs.getInt("created_by"));
        //createdByUser.setUserFullName(rs.getString("full_name"));
        dtoObj.setCreatedBy(createdByUser);
        dtoObj.setResolutionDate(rs.getTimestamp("resolution_date"));
        dtoObj.setIsResolved(rs.getInt("is_incident_resolved"));
        dtoObj.setOrganizationName(rs.getString("organization_name"));
        dtoObj.setIsIncidentNew(rs.getInt("is_verified"));
        dtoObj.setGeneratedIncidentId(rs.getString("unique_incident_seq"));
        
        return dtoObj;
    }*/

    
}
