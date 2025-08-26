package com.iassure.incident.dao;

import com.iassure.incident.dto.IncidentsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Jyothi.T
 */
@Repository
public class IncidentsDAOImpl implements IncidentsDAO {

    private static final Logger logger = LoggerFactory.getLogger(IncidentsDAOImpl.class);

    @Autowired
    JdbcTemplate primaryJdbcTemplate;

    @Override
    public List<IncidentsDTO> retrieveIncidents(String type, boolean isManager, List<Integer> orgazinationIdList) {

        List<IncidentsDTO> resultList = new ArrayList<>();

        try {
            StringBuilder sbQry = new StringBuilder();
            sbQry.append("SELECT id.pk_incident_id,id.subject,id.description,id.category, ");
            sbQry.append("id.severity,id.created_on,id.created_by,id.resolution_date,id.is_incident_resolved,is_verified,unique_incident_seq,ud.full_name,od.organization_name ");
            sbQry.append("FROM incident_details id ");
            sbQry.append("LEFT JOIN user_details ud ON ud.pk_user_details_id=id.created_by  ");
            sbQry.append("LEFT JOIN organization_details od on id.fk_organization_id = od.pk_organization_id ");
            if (type != null && type.equalsIgnoreCase("open")) {
                sbQry.append("where is_incident_resolved = 0 ");
            }
            if (type != null && type.equalsIgnoreCase("resolved")) {
                sbQry.append("where is_incident_resolved = 1 ");
            }

            if (isManager) {
                if (orgazinationIdList != null && orgazinationIdList.size() > 0) {
                    String organizationParameter = null;
                    for (int i = 0; i < orgazinationIdList.size(); i++) {
                        if (i == 0) {
                            organizationParameter = String.valueOf(orgazinationIdList.get(i));
                        } else {
                            organizationParameter = organizationParameter + "," + String.valueOf(orgazinationIdList.get(i));
                        }
                    }
                    if (type != null && type.equalsIgnoreCase("total")) {
                        sbQry.append("where id.fk_organization_id in(" + organizationParameter + ") ");
                    } else {
                        sbQry.append(" and id.fk_organization_id in(" + organizationParameter + ") ");
                    }
                    sbQry.append("ORDER BY created_on desc");
                   /* resultList = primaryJdbcTemplate.query(sbQry.toString(), new IncidentsRowMapper());*/
                }
            } else {
                sbQry.append("ORDER BY created_on desc");
              /*  resultList = primaryJdbcTemplate.query(sbQry.toString(), new IncidentsRowMapper());*/
            }

        } catch (Exception ex) {
            System.out.println("Exception in retrieveIncidents() " + ex.getMessage());
            logger.error("Exception in retrieveIncidents() " + ex.getMessage());
        }

        return resultList;
    }

    @Override
    public int saveIncident(IncidentsDTO dataDTO) {
        int generatedKeyValue = 0;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            StringBuilder sbQry = new StringBuilder();
            sbQry.append("INSERT INTO incident_details(subject,description,category,fk_organization_id,severity,created_on,created_by) ");
            sbQry.append("VALUES (?,?,?,?,?,?,?) ");
            primaryJdbcTemplate.update((Connection connection) -> {

                PreparedStatement preparedStatement;
                preparedStatement = connection.prepareStatement(sbQry.toString(), Statement.RETURN_GENERATED_KEYS);
                /*if (dataDTO.getSubject() != null) {
                    preparedStatement.setString(1, dataDTO.getSubject());
                } else {
                    preparedStatement.setNull(1, java.sql.Types.VARCHAR);
                }
                if (dataDTO.getDescription() != null) {
                    preparedStatement.setString(2, dataDTO.getDescription());
                } else {
                    preparedStatement.setNull(2, java.sql.Types.VARCHAR);
                }
                if (dataDTO.getCategory() != null) {
                    preparedStatement.setString(3, dataDTO.getCategory());
                } else {
                    preparedStatement.setNull(3, java.sql.Types.VARCHAR);
                }*/
                if (dataDTO.getCategoryId() != null && dataDTO.getCategoryId() != 0) {
                    preparedStatement.setInt(4, dataDTO.getCategoryId());
                } else {
                    preparedStatement.setNull(4, java.sql.Types.INTEGER);
                }
               /* preparedStatement.setInt(5, dataDTO.getSeverity());
                preparedStatement.setTimestamp(6, dataDTO.getCreatedOn());
                if (dataDTO.getCreatedBy().getUserId() != null && dataDTO.getCreatedBy().getUserId() != 0) {
                    preparedStatement.setInt(7, dataDTO.getCreatedBy().getUserId());
                } else {
                    preparedStatement.setNull(7, java.sql.Types.INTEGER);
                }*/
                // preparedStatement.setTimestamp(8, dataDTO.getResolutionDate());

                return preparedStatement;

            }, keyHolder);
            generatedKeyValue = keyHolder.getKey().intValue();

        } catch (Exception ex) {

            System.out.println("Error in saveIncident(IncidentDTO dataDTO) " + ex.getMessage());
            logger.error("Error in saveIncident(IncidentDTO dataDTO) " + ex.getMessage());
        }
        return generatedKeyValue;
    }

    @Override
    public boolean updateIncident(IncidentsDTO dataDTO) {

        boolean result = false;
        /*
        try {
            StringBuilder sbQry = new StringBuilder();
            sbQry.append("UPDATE suppliers_master SET ");
            sbQry.append("supplier_name = ? , ");
            sbQry.append("address = ? , ");
            sbQry.append("contact_no = ? , ");
            sbQry.append("email = ? , ");
            sbQry.append("description = ? , ");
            sbQry.append("fk_status_id = ? , ");
            sbQry.append("updated_on= ?, ");
            sbQry.append("updated_by= ?, ");
            sbQry.append("street_no= ?, ");
            sbQry.append("street= ?, ");
            sbQry.append("suburb= ?, ");
            sbQry.append("state= ?, ");
            sbQry.append("postcode= ?, ");
            sbQry.append("country= ? ");
            sbQry.append("WHERE pk_supplier_id = ? ");
            int updateResult = primaryJdbcTemplate.update((Connection connection) -> {

                PreparedStatement preparedStatement;
                preparedStatement = connection.prepareStatement(sbQry.toString(), Statement.RETURN_GENERATED_KEYS);

                if (dataDTO.getIncidentName() != null) {
                    preparedStatement.setString(1, dataDTO.getIncidentName());
                } else {
                    preparedStatement.setNull(1, java.sql.Types.VARCHAR);
                }
                if (dataDTO.getAddress() != null) {
                    preparedStatement.setString(2, dataDTO.getAddress());
                } else {
                    preparedStatement.setNull(2, java.sql.Types.VARCHAR);
                }
                if (dataDTO.getContactNo() != null) {
                    preparedStatement.setString(3, dataDTO.getContactNo());
                } else {
                    preparedStatement.setNull(3, java.sql.Types.VARCHAR);
                }
                if (dataDTO.getEmail() != null) {
                    preparedStatement.setString(4, dataDTO.getEmail());
                } else {
                    preparedStatement.setNull(4, java.sql.Types.VARCHAR);
                }
                if (dataDTO.getDescription()!= null) {
                    preparedStatement.setString(5, dataDTO.getDescription());
                } else {
                    preparedStatement.setNull(5, java.sql.Types.VARCHAR);
                }
                if (dataDTO.getStatusDetails().getStatusId() != null) {
                    preparedStatement.setInt(6, dataDTO.getStatusDetails().getStatusId());
                } else {
                    preparedStatement.setNull(6, java.sql.Types.INTEGER);
                }

                preparedStatement.setTimestamp(7, dataDTO.getUpdatedOn());
                if (dataDTO.getUpdatedBy().getUserId()!= null && dataDTO.getUpdatedBy().getUserId()!= 0) {
                    preparedStatement.setInt(8, dataDTO.getUpdatedBy().getUserId());
                } else {
                    preparedStatement.setNull(8, java.sql.Types.INTEGER);
                }
                if (dataDTO.getStreetNo()!= null) {
                    preparedStatement.setString(9, dataDTO.getStreetNo());
                } else {
                    preparedStatement.setNull(9, java.sql.Types.VARCHAR);
                }
                if (dataDTO.getStreet()!= null) {
                    preparedStatement.setString(10, dataDTO.getStreet());
                } else {
                    preparedStatement.setNull(10, java.sql.Types.VARCHAR);
                }
                if (dataDTO.getSuburb()!= null) {
                    preparedStatement.setString(11, dataDTO.getSuburb());
                } else {
                    preparedStatement.setNull(11, java.sql.Types.VARCHAR);
                }
                if (dataDTO.getState()!= null) {
                    preparedStatement.setString(12, dataDTO.getState());
                } else {
                    preparedStatement.setNull(12, java.sql.Types.VARCHAR);
                }
                if (dataDTO.getPostcode() != null) {
                    preparedStatement.setInt(13, dataDTO.getPostcode());
                } else {
                    preparedStatement.setNull(13, java.sql.Types.INTEGER);
                }
                if (dataDTO.getCountry()!= null) {
                    preparedStatement.setString(14, dataDTO.getCountry());
                } else {
                    preparedStatement.setNull(14, java.sql.Types.VARCHAR);
                }
                preparedStatement.setInt(15, dataDTO.getIncidentId());

                return preparedStatement;

            });
            if (updateResult > 0) {
                result = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error in updateIncident(IncidentDTO dataDTO) " + ex.getMessage());
            logger.error("Error in updateIncident(IncidentDTO dataDTO) " + ex.getMessage());
            result = false;
        }
         */
        return result;
    }

    @Override
    public IncidentsDTO retrieveIncidentById(int id) {

        IncidentsDTO result = new IncidentsDTO();
        try {
            StringBuilder sbQry = new StringBuilder();

            sbQry.append("SELECT id.pk_incident_id,id.subject,id.description,id.category, ");
            sbQry.append("id.severity,id.created_on,id.created_by,id.resolution_date,is_incident_resolved,is_verified,unique_incident_seq,ud.full_name,od.organization_name ");
            sbQry.append("FROM incident_details id ");
            sbQry.append("LEFT JOIN user_details ud ON ud.pk_user_details_id=id.created_by  ");
            sbQry.append("LEFT JOIN organization_details od on id.fk_organization_id = od.pk_organization_id ");
            sbQry.append("WHERE id.pk_incident_id = ? ");
            sbQry.append("ORDER BY created_on ");

            /*result = primaryJdbcTemplate.queryForObject(sbQry.toString(), new Object[]{id}, new IncidentsRowMapper());*/
        } catch (Exception ex) {
            System.out.println("Error in retrieveIncidentById(int id) " + ex.getMessage());
            logger.error("Error in retrieveIncidentById(int id) " + ex.getMessage());

        }

        return result;
    }

    @Override
    public String getUserNameById(int createdBy) {
        String userName = null;
        try {
            String sql = "SELECT full_name from user_details where pk_user_details_id = ?";

            userName = primaryJdbcTemplate.queryForObject(sql, new Object[]{createdBy}, String.class);
        } catch (Exception e) {
        }
        return userName;
    }

    @Override
    public Map<String, Object> getOrganizationAndTypeDetails(int userId) {

        Map<String, Object> userTypeDetails = null;
        try {

            String sql = "SELECT um.user_type,u.fk_organization_id FROM user_management_db.user_type_master um inner join user_details u on um.pk_user_type_id = u.fk_user_type_id where u.pk_user_details_id = ?";;

            userTypeDetails = primaryJdbcTemplate.queryForMap(sql, userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userTypeDetails;
    }

   /* @Override
    public Map<String, Integer> getIncidentCountDetails(HttpServletRequest request, String organizationParameter, boolean isManager) {
        Map<String, Integer> incidentCountDetails = new HashMap<>();
        Map<String, Object> mapObj = new HashMap<>();
        try {
            StringBuilder sb = new StringBuilder();

            if (isManager) {
                organizationParameter = (organizationParameter != null) ? organizationParameter : String.valueOf(0);
                sb.append("select * from (select count(*) as unresolved from incident_details where is_incident_resolved = 0 and fk_organization_id in(" + organizationParameter + ") ) unresolved, ");
                sb.append(" (select count(*) as resolved from incident_details where is_incident_resolved = 1 and fk_organization_id in(" + organizationParameter + "))  resolved ,");
                sb.append(" (select count(*) as total from incident_details where fk_organization_id in(" + organizationParameter + ")) total ; ");
                mapObj = primaryJdbcTemplate.queryForMap(sb.toString());
            } else {
                sb.append("select * from (select count(*) as unresolved from incident_details where is_incident_resolved = 0 ) unresolved, ");
                sb.append(" (select count(*) as resolved from incident_details where is_incident_resolved = 1)  resolved ,");
                sb.append(" (select count(*) as total from incident_details )  total ; ");
                mapObj = primaryJdbcTemplate.queryForMap(sb.toString());
            }

            mapObj.entrySet().stream().forEach(map
                    -> {
                if (map.getKey().equals("unresolved")) {
                    Long l = new Long((long) map.getValue());
                    incidentCountDetails.put("unresolved", l.intValue());
                } else if (map.getKey().equals("resolved")) {
                    Long l = new Long((long) map.getValue());
                    incidentCountDetails.put("resolved", l.intValue());
                } else if (map.getKey().equals("total")) {
                    Long l = new Long((long) map.getValue());
                    incidentCountDetails.put("total", l.intValue());
                }
            });
        } catch (Exception e) {
            logger.error("Exception occured in the getIncidentCountDetails method" + e.getMessage());
        }
        return incidentCountDetails;
    }

    @Override
    public Map<String, Integer> getIncidentCountDetailsByUserId(HttpServletRequest request, int organizationId) {

        Map<String, Integer> incidentCountDetails = new HashMap<>();
        Map<String, Object> mapObj = new HashMap<>();
        int loggedUserTypeId = (int) request.getSession().getAttribute("loggedUserTypeId"); 
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("select * from (select count(*) as unresolved from incident_details where is_incident_resolved = 0 ");
           
            //If internal user then don't fetch other Organization Details
            if(loggedUserTypeId == 4) {
            sb.append(" and fk_organization_id = " + organizationId);
            }
            sb.append(  " ) unresolved, ");
            sb.append(" (select count(*) as resolved from incident_details where is_incident_resolved = 1 ");
            if(loggedUserTypeId == 4) {
                sb.append(" and fk_organization_id = " + organizationId);
                }
             sb.append(  " )  resolved ,");
            sb.append(" (select count(*) as total from incident_details" );
            if(loggedUserTypeId == 4) {
                sb.append(" where fk_organization_id = " + organizationId);
                }
            sb.append(  " ) total ; ");
            mapObj = primaryJdbcTemplate.queryForMap(sb.toString());
            mapObj.entrySet().stream().forEach(map
                    -> {
                if (map.getKey().equals("unresolved")) {
                    Long l = new Long((long) map.getValue());
                    incidentCountDetails.put("unresolved", l.intValue());
                } else if (map.getKey().equals("resolved")) {
                    Long l = new Long((long) map.getValue());
                    incidentCountDetails.put("resolved", l.intValue());
                } else if (map.getKey().equals("total")) {
                    Long l = new Long((long) map.getValue());
                    incidentCountDetails.put("total", l.intValue());
                }
            });
        } catch (Exception e) {
            logger.error("Exception occured in the getIncidentCountDetailsByUserId method" + e.getMessage());
        }
        return incidentCountDetails;
    }*/
}
