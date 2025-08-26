/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iassure.incident.dao;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Jyothi.T
 */
@Repository
public class IncidentActionLogDAOImpl implements IncidentActionLogDAO{
    
    private static final Logger logger = LoggerFactory.getLogger(IncidentActionLogDAOImpl.class);
   
    @Autowired
    JdbcTemplate primaryJdbcTemplate;
    
    /*@Override
    public int saveIncidentActionLog(IncidentActionLogDTO dataDTO){
        int generatedKeyValue = 0;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            StringBuilder sbQry = new StringBuilder();
            sbQry.append("INSERT INTO incident_action_log(fk_incident_id,investigation_findings,corrective_actions,preventative_actions,case_type,fk_case_status,created_on,created_by,is_incident_specific_to_job,fk_job_scheduling_id,is_incident_resolved) ");
            sbQry.append("VALUES (?,?,?,?,?,?,?,?,?,?,?) ");
            primaryJdbcTemplate.update((Connection connection) -> {

                PreparedStatement preparedStatement;
                preparedStatement = connection.prepareStatement(sbQry.toString(), Statement.RETURN_GENERATED_KEYS);
                if (dataDTO.getIncidentDetails().getIncidentId()!= null) {
                    preparedStatement.setInt(1, dataDTO.getIncidentDetails().getIncidentId());
                } else {
                    preparedStatement.setNull(1, java.sql.Types.INTEGER);
                }
                if (dataDTO.getInvestigation_findings()!= null) {
                    preparedStatement.setString(2, dataDTO.getInvestigation_findings());
                } else {
                    preparedStatement.setNull(2, java.sql.Types.VARCHAR);
                }
                if (dataDTO.getCorrective_actions()!= null) {
                    preparedStatement.setString(3, dataDTO.getCorrective_actions());
                } else {
                    preparedStatement.setNull(3, java.sql.Types.VARCHAR);
                }                
                if (dataDTO.getPreventative_actions()!= null) {
                    preparedStatement.setString(4, dataDTO.getPreventative_actions());
                } else {
                    preparedStatement.setNull(4, java.sql.Types.VARCHAR);
                } 
                if (dataDTO.getCaseType()!= null) {
                    preparedStatement.setString(5, dataDTO.getCaseType());
                } else {
                    preparedStatement.setNull(5, java.sql.Types.VARCHAR);
                }
                if (dataDTO.getStatusDetails().getStatusId() != null) {
                    preparedStatement.setInt(6, dataDTO.getStatusDetails().getStatusId());
                } else {
                    preparedStatement.setNull(6, java.sql.Types.INTEGER);
                }
                preparedStatement.setTimestamp(7, dataDTO.getCreatedOn());
                if (dataDTO.getCreatedBy().getUserId()!= null && dataDTO.getCreatedBy().getUserId()!= 0) {
                    preparedStatement.setInt(8, dataDTO.getCreatedBy().getUserId());
                } else {
                    preparedStatement.setNull(8, java.sql.Types.INTEGER);
                }
                preparedStatement.setInt(9, dataDTO.getIncidentSpecificToJobFlag());
                if(dataDTO.getFkJobSchedulingId() != null && dataDTO.getFkJobSchedulingId() != 0) {
                	preparedStatement.setInt(10, dataDTO.getFkJobSchedulingId());
                }else {
                	preparedStatement.setNull(10, java.sql.Types.INTEGER);
                }
                preparedStatement.setInt(11, dataDTO.getIncidentResolvedFlag());
                return preparedStatement;

            }, keyHolder);
            generatedKeyValue = keyHolder.getKey().intValue();

        } catch (Exception ex) {

            System.out.println("Error in saveIncidentActionLog(IncidentActionLogDTO dataDTO) " + ex.getMessage());
            logger.error("Error in saveIncidentActionLog(IncidentActionLogDTO dataDTO) " + ex.getMessage());
        }
        return generatedKeyValue;
    }
    */
}
