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
public class IncidentCorrectiveActionPlanDAOImpl implements IncidentCorrectiveActionPlanDAO{
    
    private static final Logger logger = LoggerFactory.getLogger(IncidentCorrectiveActionPlanDAOImpl.class);
   
    @Autowired
    JdbcTemplate primaryJdbcTemplate;
    
   /* @Override
    public boolean saveIncidentCorrectiveActionPlan(List<IncidentCorrectiveActionPlanDTO> actionPlanList)
    {
        boolean result = false;
        for (IncidentCorrectiveActionPlanDTO dataDTO : actionPlanList) {
            int generatedKeyValue = 0;
            KeyHolder keyHolder = new GeneratedKeyHolder();
            try {
                StringBuilder sbQry = new StringBuilder();
                sbQry.append("INSERT INTO incident_corrective_action_plan(fk_incident_action_log_id,task,action_taken,is_incident_resolved,action_date) ");
                sbQry.append("VALUES (?,?,?,?,?) ");
                primaryJdbcTemplate.update((Connection connection) -> {

                    PreparedStatement preparedStatement;
                    preparedStatement = connection.prepareStatement(sbQry.toString(), Statement.RETURN_GENERATED_KEYS);
                    if (dataDTO.getActionLogDetails().getIncidentActionLogId()!= null) {
                        preparedStatement.setInt(1, dataDTO.getActionLogDetails().getIncidentActionLogId());
                    } else {
                        preparedStatement.setNull(1, java.sql.Types.INTEGER);
                    }
                    if (dataDTO.getTask()!= null) {
                        preparedStatement.setString(2, dataDTO.getTask());
                    } else {
                        preparedStatement.setNull(2, java.sql.Types.VARCHAR);
                    }
                    if (dataDTO.getActionTaken()!= null) {
                        preparedStatement.setString(3, dataDTO.getActionTaken());
                    } else {
                        preparedStatement.setNull(3, java.sql.Types.VARCHAR);
                    }
                        preparedStatement.setInt(4, dataDTO.getCorrectiveActionResolved());
                    preparedStatement.setDate(5, dataDTO.getActionDate());
                    return preparedStatement;

                }, keyHolder);
                generatedKeyValue = keyHolder.getKey().intValue();
                result = true;

            } catch (Exception ex) {
                result = false;
                System.out.println("Error in saveIncidentCorrectiveActionPlan(List<IncidentCorrectiveActionPlanDTO> actionPlanList) " + ex.getMessage());
                logger.error("Error in saveIncidentCorrectiveActionPlan(List<IncidentCorrectiveActionPlanDTO> actionPlanList) " + ex.getMessage());
            }
        }
        return result;
    }*/
    
}
