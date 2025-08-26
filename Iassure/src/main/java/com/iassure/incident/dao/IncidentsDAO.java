/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iassure.incident.dao;


import com.iassure.incident.dto.IncidentsDTO;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Jyothi.T
 */
public interface IncidentsDAO {
    
    public List<IncidentsDTO> retrieveIncidents(String type,  boolean isManager, List<Integer> orgazinationIdList);
    
    public int saveIncident(IncidentsDTO dataDTO);

    public boolean updateIncident(IncidentsDTO client);

    public IncidentsDTO retrieveIncidentById(int id);

	public String getUserNameById(int createdBy);

	public Map<String, Object> getOrganizationAndTypeDetails(int userId);

	//public Map<String, Integer> getIncidentCountDetails(HttpServletRequest request, String organizationParameter, boolean isManager);

	//public Map<String, Integer> getIncidentCountDetailsByUserId(HttpServletRequest request, int userId);



}
