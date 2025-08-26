package com.iassure.incident.service;

import com.iassure.incident.dto.*;
import com.iassure.incident.entity.Fields;
import com.iassure.incident.entity.ProblemRootCause;
import com.iassure.incident.response.*;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author Karthik S
 */
public interface IncidentService {


    IncidentByIdResponse getIncidentDetailsById(Integer incidentId, Integer orgId, Integer userId);

    GetPreventiveActionResponse getPreventiveActionDetails(int orgId, int incidentId, int userId);

    GetHistoryResponse getIncidentHistory(int incidentId);

    IncidentCountResponse getIncidentCountDetails(Integer orgId, Integer userId);

    String createIncidentWithAI(IncidentsDTO incidentDTO) throws IOException;

    String createTasksWithAI(IncidentsDTO incidentDTO) throws IOException;

    List<String> getSuggestions(IncidentsDTO incidentsDTO) throws IOException;

    String createDashboardAI(IncidentsDTO incidentDTO) throws IOException;

    AddIncidentResponse createIncidentByUser(IncidentsDTO incidentDTO, List<MultipartFile> files);

    IncidentDetailsDashboardResponse fetchIncidentDetailsJson(Integer orgId, Integer incidentStatusId, Integer assignedUserId);

    GetIncidentTasksResponse getTaskDetails(RequestIncidentDetailsById requestBody);

    GetCapDetailsResponse getCapDetails(RequestIncidentDetailsById requestBody);

    SaveInterimResponse getInterimDetails(RequestIncidentDetailsById requestBodyDTO);

    GetRootCauseResponse getRootCauseDetails(RequestIncidentDetailsById requestIncidentDetailsById);

    TaskMasterResponse addTasksWithDepartment(RequestTaskMaster requestTaskMaster);

    AddMasterSourceResponse addMasterListByType(RequestMasterSource requestMasterSource);

    FetchTaskResponse fetchTasksByDepartment(int departmentId);

    MasterSourceResponse getMastersListByType(String sourceType);

    PreventiveActionResponse savePreventiveActionResponse(PreventiveActionDTO preventiveActionDTO, List<MultipartFile> files);

    RootCauseResponse saveRootCauseAction(RootCauseAnalysis rootCauseAnalysis, List<MultipartFile> files);

    StatusResponse deleteProblemRootCause(ProblemRootCause problemRootCause);

    SaveInterimResponse saveIncidentInterim(RequestIncidentInterim requestIncidentInterim, List<MultipartFile> files);

    CorrectiveActionResponse saveCorrectiveAction(CorrectiveActionPlan correctiveActionPlan, List<MultipartFile> files);

    GetTasksResponse saveTasksForCap(RequestCAPTask requestCAPTask, List<MultipartFile> files);

    IncidentChatResponse saveIncidentChat(RequestChatDto requestChatDto);

    IncidentChatResponse getChatByIncidentId(RequestChatDto requestChatDto);

    GetIncidentAssignResponse getIncidentAssignDetails(RequestIncidentAssign requestIncidentAssign);

    StatusResponse closeIncident(RequestChatDto requestChatDto);

    NotificationResponse getNotifications();

    StatusResponse deleteField(int id);

    StatusResponse deleteIncidentField(int incidentId, int fieldId);

    FieldsResponse getAllFields();

    Fields saveField(Fields field);

    List<IncidentFieldsDTO> getFieldsByIncidentId(Integer incidentId);

    IncidentAssignResponse assignIncident(RequestIncidentAssign requestIncidentAssign);

    ResponseEntity<InputStreamResource> getReport(int incidentId, int orgId, int userid);
}