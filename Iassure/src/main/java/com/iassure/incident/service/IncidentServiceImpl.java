package com.iassure.incident.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iassure.constants.AppConstants;
import com.iassure.incident.dto.*;
import com.iassure.incident.entity.*;
import com.iassure.incident.repository.*;
import com.iassure.incident.response.*;
import com.iassure.usermanagement.response.GetUserByIdResponse;
import com.iassure.usermanagement.service.UserManagementService;
import com.iassure.util.DateUtil;
import com.iassure.util.EmailUtil;
import com.iassure.util.OpenAIUtil;
import com.iassure.util.ReportUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.*;

import static com.iassure.constants.AppConstants.*;

/**
 * @author Karthik S
 */
@Service
@Log4j2
@Transactional
public class IncidentServiceImpl implements IncidentService {

    @Autowired
    IncidentDetailsRepository incidentDetailsRepository;

    @Autowired
    EmailUtil emailUtil;

    @Autowired
    IncidentFieldRepo incidentFieldRepo;

    @Autowired
    ReportUtil reportUtil;
    @Autowired
    GetIncidentAssignRepository getIncidentAssignRepository;

    @Autowired
    IncidentHistoryRepo incidentHistoryRepo;
    @Autowired
    IncidentDashboardRepo incidentDashboardRepo;
    @Autowired
    IncidentAssignRepository incidentAssignRepository;
    @Autowired
    IncidentCountRepo incidentCountRepo;
    @Autowired
    CAPTaskRepository capTaskRepository;
    @Autowired
    DateUtil dateUtil;
    @Autowired
    StorageService storageService;
    @Autowired
    UserManagementService userService;
    @Autowired
    CorrectiveActionPlanMasterRepository correctiveActionPlanMasterRepository;
    @Autowired
    IncidentNotificationRepo incidentNotificationRepo;
    @Autowired
    MasterSourceRepository masterSourceRepository;
    @Autowired
    PreventiveActionRepository preventiveActionRepository;
    @Autowired
    IncidentDetailsByIdRepo incidentDetailsByIdRepo;
    @Autowired
    GetPreventiveActionRepo getPreventiveActionRepo;
    @Autowired
    IncidentInterimRepo incidentInterimRepo;
    @Autowired
    GetCapTaskRepo getCapTaskRepo;
    @Autowired
    IncidentRootCauseRepository incidentRootCauseRepository;
    @Autowired
    ProblemRootCauseRepository problemRootCauseRepository;
    @Autowired
    GetIncidentRootCauseRepo getIncidentRootCauseRepo;
    @Autowired
    FieldsRepository fieldsRepository;
    @Autowired
    UserManagementService userManagementService;
    @Autowired
    TaskMasterRepository taskMasterRepository;
    @Autowired
    OpenAIUtil openAIUtil;
    @Autowired
    IncidentChatRepository incidentChatRepository;
    @Autowired
    GetIncidentChatRepo getIncidentChatRepo;
    @PersistenceContext
    private EntityManager entityManager;

    private static IncidentCorrectiveActionPlan getIncidentCorrectiveActionPlan(CorrectiveActionPlan correctiveActionPlan) {
        IncidentCorrectiveActionPlan incidentCorrectiveActionPlan = new IncidentCorrectiveActionPlan();
        boolean isIncidentActionPlanExist = correctiveActionPlan.getIncidentActionPlanId() == null || correctiveActionPlan.getIncidentActionPlanId() == 0;
        if (!isIncidentActionPlanExist) {
            incidentCorrectiveActionPlan.setIncidentActionPlanId(correctiveActionPlan.getIncidentActionPlanId());
        }
        incidentCorrectiveActionPlan.setIncidentId(correctiveActionPlan.getIncidentId());
        incidentCorrectiveActionPlan.setComments(correctiveActionPlan.getComments());
        incidentCorrectiveActionPlan.setCreatedBy(correctiveActionPlan.getUserId());
        return incidentCorrectiveActionPlan;
    }

    @Transactional
    public String executeSqlQuery(String sql) throws Exception {
        // Execute SQL query and retrieve results as Tuple
        List<Tuple> results = entityManager.createNativeQuery(sql, Tuple.class).getResultList();

        // List to store each row as a map of column names to values
        List<Map<String, Object>> resultList = new ArrayList<>();

        for (Tuple tuple : results) {
            Map<String, Object> row = new HashMap<>();
            tuple.getElements().forEach(element -> {
                String columnName = element.getAlias();
                Object value = tuple.get(columnName);
                row.put(columnName, value);
            });
            resultList.add(row);
        }

        // Convert the list of maps to JSON format
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(resultList);
    }

    @Override
    public IncidentByIdResponse getIncidentDetailsById(Integer incidentId, Integer orgId, Integer userId) {
        log.info("In getIncidentDetailsById method");
        StatusResponse statusResponse = new StatusResponse();
        List<DocumentEntity> incidentFiles = new ArrayList<>();
        try {

            IncidentDetailsById incidentDetails = incidentDetailsByIdRepo.getIncidentDetailsById(orgId, incidentId, userId);
            incidentFiles = storageService.getAllFileDetails(incidentId, "Incident");
            if (incidentDetails != null) {
                statusResponse.setResponseCode(200);
                statusResponse.setResponseMessage("Incident Details fetch Successful");
                String summary = openAIUtil.summarizeIncident(incidentDetails);
                return new IncidentByIdResponse(statusResponse, incidentDetails, summary, incidentFiles);
            } else {
                statusResponse.setResponseCode(400);
                statusResponse.setResponseMessage("Invalid Parameters");
                return new IncidentByIdResponse(statusResponse, null, null, incidentFiles);
            }
        } catch (Exception e) {
            log.error("Exception occurred in getIncidentDetailsById {} ", e.getMessage());
            statusResponse.setResponseCode(500);
            statusResponse.setResponseMessage(INTERNAL_SERVER_ERROR);
            return new IncidentByIdResponse(statusResponse, null, null, incidentFiles);
        }

    }

    @Override
    public GetPreventiveActionResponse getPreventiveActionDetails(int orgId, int incidentId, int userId) {
        StatusResponse statusResponse = new StatusResponse();
        List<DocumentEntity> preventiveActionFiles = new ArrayList<>();

        try {
            GetPreventiveActionDetails preventiveActionDetails = getPreventiveActionRepo.getPreventiveActionDetails(orgId, incidentId, userId);
            preventiveActionFiles = storageService.getAllFileDetails(incidentId, "PA");
            statusResponse.setResponseCode(200);
            if (preventiveActionDetails != null) {
                statusResponse.setResponseMessage("Preventive Action Details Fetch Success");
                return new GetPreventiveActionResponse(statusResponse, preventiveActionDetails, preventiveActionFiles);
            } else {
                statusResponse.setResponseMessage("No Records");
                return new GetPreventiveActionResponse(statusResponse, null, null);
            }
        } catch (Exception e) {
            log.error("Exception occurred in getPreventiveActionDetails method {} ", e.getMessage());
            statusResponse.setResponseMessage(INTERNAL_SERVER_ERROR);
            statusResponse.setResponseCode(500);
            return new GetPreventiveActionResponse(statusResponse, null, null);
        }
    }

    @Override
    public GetIncidentTasksResponse getTaskDetails(RequestIncidentDetailsById requestBody) {
        StatusResponse statusResponse = new StatusResponse();
        List<GetCapTask> tasks = new ArrayList<>();
        List<DocumentEntity> files = new ArrayList<>();

        try {
            tasks = getCapTaskRepo.getCapTaskDetails(requestBody.getOrgId(), requestBody.getIncidentId());
            files = storageService.getAllFileDetails(requestBody.getIncidentId(), "Tasks");
            statusResponse.setResponseMessage("Task Details Fetch Successful");
            statusResponse.setResponseCode(200);
        } catch (Exception e) {
            statusResponse.setResponseCode(500);
            statusResponse.setResponseMessage(INTERNAL_SERVER_ERROR);
        }
        return new GetIncidentTasksResponse(statusResponse, tasks, files);
    }

    @Override
    public GetCapDetailsResponse getCapDetails(RequestIncidentDetailsById requestBody) {
        StatusResponse statusResponse = new StatusResponse();
        IncidentCorrectiveActionPlan incidentCorrectiveActionPlan = new IncidentCorrectiveActionPlan();
        List<DocumentEntity> files = new ArrayList<>();
        try {
            incidentCorrectiveActionPlan = correctiveActionPlanMasterRepository.getCorrectiveActionDetails(requestBody.getOrgId(), requestBody.getIncidentId(), requestBody.getUserId());
            files = storageService.getAllFileDetails(incidentCorrectiveActionPlan.getIncidentId(), "CAP");

            statusResponse.setResponseCode(200);
            statusResponse.setResponseMessage("CorrectiveActionPlan details fetch successful");
        } catch (Exception e) {
            log.error("Exception occurred in getCapDetails {} ", e.getMessage());
            statusResponse.setResponseMessage(INTERNAL_SERVER_ERROR);
            statusResponse.setResponseCode(500);
        }
        return new GetCapDetailsResponse(statusResponse, incidentCorrectiveActionPlan, files);
    }

    @Override
    public SaveInterimResponse getInterimDetails(RequestIncidentDetailsById requestBodyDTO) {
        StatusResponse statusResponse = new StatusResponse();
        IncidentInterim incidentInterim = new IncidentInterim();
        List<DocumentEntity> interimFiles = new ArrayList<>();
        try {
            incidentInterim = incidentInterimRepo.getInterimDetails(requestBodyDTO.getOrgId(), requestBodyDTO.getIncidentId(), requestBodyDTO.getUserId());
            interimFiles = storageService.getAllFileDetails(requestBodyDTO.getIncidentId(), "Interim");
            statusResponse.setResponseCode(200);
            if (incidentInterim != null) {
                statusResponse.setResponseMessage("Interim Details fetched successfully");
            } else {
                statusResponse.setResponseMessage("No Records");
            }
        } catch (Exception e) {
            log.error("Exception occurred in getInterimDetails method {}", e.getMessage());
            statusResponse.setResponseMessage(INTERNAL_SERVER_ERROR);
            statusResponse.setResponseCode(500);
        }
        return new SaveInterimResponse(statusResponse, incidentInterim, interimFiles);
    }

    @Override
    public GetRootCauseResponse getRootCauseDetails(RequestIncidentDetailsById requestIncidentDetailsById) {
        log.info("In getRootCauseDetails method start");
        StatusResponse statusResponse = new StatusResponse();
        int orgId = requestIncidentDetailsById.getOrgId();
        int incidentId = requestIncidentDetailsById.getIncidentId();
        int userId = requestIncidentDetailsById.getUserId();
        GetIncidentRootCause getIncidentRootCause = new GetIncidentRootCause();
        List<ProblemRootCause> rootCauses = new ArrayList<>();
        List<DocumentEntity> rootCauseFiles = new ArrayList<>();
        try {
            getIncidentRootCause = getIncidentRootCauseRepo.getRootCauseDetails(orgId, incidentId, userId);
            rootCauses = problemRootCauseRepository.getProblemDetails(orgId, incidentId);
            rootCauseFiles = storageService.getAllFileDetails(incidentId, "RootCause");
            if (getIncidentRootCause != null && !rootCauses.isEmpty()) {
                statusResponse.setResponseCode(200);
                statusResponse.setResponseMessage("Root Cause Details Fetch Success");
            } else {
                statusResponse.setResponseCode(400);
                statusResponse.setResponseMessage("No records");
            }
        } catch (Exception e) {
            log.error("Exception occurred in getRootCauseDetails method {} ", e.getMessage());
            statusResponse.setResponseMessage(INTERNAL_SERVER_ERROR);
            statusResponse.setResponseCode(500);
        }
        return new GetRootCauseResponse(statusResponse, getIncidentRootCause, rootCauses, rootCauseFiles);
    }

    @Override
    public GetHistoryResponse getIncidentHistory(int incidentId) {
        StatusResponse statusResponse = new StatusResponse();
        List<IncidentHistory> incidentHistories = new ArrayList<>();
        try {
            incidentHistories = incidentHistoryRepo.getIncidentHistory(incidentId);
            if (!incidentHistories.isEmpty()) {
                statusResponse.setResponseCode(200);
                statusResponse.setResponseMessage("Incident Histories Fetch Successful");
            } else {
                statusResponse.setResponseCode(400);
                statusResponse.setResponseMessage("No History");
            }
        } catch (Exception e) {
            statusResponse.setResponseCode(500);
            statusResponse.setResponseMessage(INTERNAL_SERVER_ERROR);
        }
        return new GetHistoryResponse(statusResponse, incidentHistories);
    }

    @Override
    public IncidentCountResponse getIncidentCountDetails(Integer orgId, Integer userId) {
        IncidentCount incidentCount = new IncidentCount();
        StatusResponse statusResponse = new StatusResponse();
        try {
            incidentCount = incidentCountRepo.getIncidentCount(orgId, userId);
            if (incidentCount != null) {
                statusResponse.setResponseCode(200);
                statusResponse.setResponseMessage("Count Retrieved Successfully");
            } else {
                statusResponse.setResponseCode(400);
                statusResponse.setResponseMessage(NO_RECORDS);
            }
        } catch (Exception e) {
            log.error("Exception occured in the getIncidentCountDetails method {}", e.getMessage());
            statusResponse.setResponseCode(500);
            statusResponse.setResponseMessage(INTERNAL_SERVER_ERROR);
        }
        return new IncidentCountResponse(statusResponse, incidentCount);
    }

    private String sanitizeJson(String rawJson) {
        // Trim whitespace and remove leading or trailing unwanted characters
        rawJson = rawJson.trim();
        // Remove unwanted characters at the start or end
        return rawJson.replaceAll("^[.,\\s]+|[.,\\s]+$", "");
    }

    @Override
    public String createIncidentWithAI(IncidentsDTO incidentDTO) throws IOException {
        // Fetch response from OpenAI
        JSONObject json = openAIUtil.createIncidentResponse(incidentDTO.getUserPrompt());
        log.debug("Raw AI Response: {}", json.toString(4));

        // Extract the JSON text and sanitize it
        String finalJson = json.getJSONArray("choices").getJSONObject(0).getString("text").trim();
        finalJson = sanitizeJson(finalJson);

        // Validate and parse the JSON
        if (!isValidJson(finalJson)) {
            throw new IOException("Invalid JSON response from AI: " + finalJson);
        }

        JSONObject jsonObject = new JSONObject(finalJson);
        return jsonObject.toString(4);
    }

    private boolean isValidJson(String jsonString) {
        try {
            new JSONObject(jsonString);
            return true;
        } catch (JSONException e) {
            return false;
        }
    }


    @Override
    public String createTasksWithAI(IncidentsDTO incidentDTO) throws IOException {
        // Validate the input prompt
        if (incidentDTO.getUserPrompt() == null || incidentDTO.getUserPrompt().isEmpty()) {
            throw new IllegalArgumentException("User prompt cannot be null or empty.");
        }

        // Get the response from OpenAI utility
        JSONObject json = openAIUtil.createTasksResponse(incidentDTO.getUserPrompt());
        log.debug("Raw AI Response: {}", json.toString(4)); // Log the raw AI response

        // Extract the message content
        String responseContent;
        try {
            responseContent = json.getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content")
                    .trim();
        } catch (Exception e) {
            throw new IOException("Failed to extract content from AI response: " + e.getMessage(), e);
        }

        // Sanitize the response content
        responseContent = sanitizeResponseContent(responseContent);

        // Parse and format the content if it's JSON formatted; otherwise, return plain text
        try {
            JSONObject jsonObject = new JSONObject(responseContent);
            return jsonObject.toString(4); // Format JSON with indentation
        } catch (JSONException e) {
            log.warn("Response is not a valid JSON object: {}", responseContent);
            return responseContent; // Return plain text if not JSON
        }
    }

    // Helper method to sanitize response content
    private String sanitizeResponseContent(String content) {
        // Trim and remove leading/trailing unwanted characters like dots, commas, or spaces
        return content.replaceAll("^[.,\\s]+|[.,\\s]+$", "");
    }


    @Override
    public List<String> getSuggestions(IncidentsDTO incidentsDTO) throws IOException {
        List<String> suggestions = new ArrayList<>();
        JSONObject json = openAIUtil.generateSuggestions(incidentsDTO.getUserPrompt());
        String responseContent = json.getJSONArray("choices")
                .getJSONObject(0)
                .getJSONObject("message")
                .getString("content")
                .trim();

        // Attempt to parse the content as JSON
        try {
            JSONObject jsonObject = new JSONObject(responseContent);
            suggestions.add(jsonObject.toString(4)); // Format JSON with indentation
        } catch (Exception e) {
            // If the response is not JSON-formatted, add the plain text response
            suggestions.add(responseContent);
        }

        return suggestions;
    }

    @Override
    public String createDashboardAI(IncidentsDTO incidentDTO) throws IOException {
        // Get the response from OpenAI utility
        JSONObject jsonResponse = openAIUtil.createSqlResponse(incidentDTO.getUserPrompt());

        // Extract the message content
        String responseContent;
        try {
            responseContent = jsonResponse.getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content")
                    .trim();
        } catch (Exception e) {
            log.error("Error parsing OpenAI response: {}", e.getMessage());
            throw new IOException("Failed to parse OpenAI response", e);
        }

        String sqlQuery = responseContent;
        log.info("Generated SQL query is: {}", sqlQuery);

        // Execute SQL and get JSON response
        String promptJson;
        try {
            promptJson = executeSqlQuery(sqlQuery);
            log.info("Generated JSON from SQL execution: {}", promptJson);
        } catch (Exception e) {
            log.error("Error executing SQL query: {}", e.getMessage());
            throw new IOException("Failed to execute SQL query", e);
        }

        // Generate dashboard response from OpenAI using SQL output
        /*String dashboardContent;
        try {
            JSONObject dashboardResponse = openAIUtil.createDashboardResponse(promptJson);
            dashboardContent = dashboardResponse.getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content")
                    .trim();
            log.info("Generated Dashboard Content: {}", dashboardContent);
        } catch (Exception e) {
            log.error("Error creating dashboard response from OpenAI: {}", e.getMessage());
            throw new IOException("Failed to create dashboard response", e);
        }*/

        return promptJson;
    }


    @Override
    public AddIncidentResponse createIncidentByUser(IncidentsDTO incidentDTO, List<MultipartFile> files) {
        StatusResponse statusResponse = new StatusResponse();
        List<DocumentEntity> incidentFiles = new ArrayList<>();
        Incidents incident = new Incidents();

        if (incidentDTO == null) {
            log.info(INVALID_PARAMETERS);
            statusResponse.setResponseMessage(INVALID_PARAMETERS);
            statusResponse.setResponseCode(400);
            return new AddIncidentResponse(statusResponse, null, null);
        }

        try {
            // Process Source, Category, Severity if available
            processMasterData(incidentDTO);

            int userId = incidentDTO.getUserId();
            boolean isNewAction = (incidentDTO.getIncidentId() == null || incidentDTO.getIncidentId() == 0);

            if (isNewAction) {
                setupNewIncident(incident, incidentDTO, userId);
            } else {
                Incidents existingIncident = incidentDetailsRepository.findById(incidentDTO.getIncidentId()).orElse(null);
                if (existingIncident != null) {
                    incident.setIncidentStatusId(existingIncident.getIncidentStatusId());
                    incident.setCreatedBy(existingIncident.getCreatedBy());
                    incident.setCreatedAt(existingIncident.getCreatedAt());
                } else {
                    statusResponse.setResponseCode(404);
                    statusResponse.setResponseMessage("Incident not found");
                    return new AddIncidentResponse(statusResponse, null, null);
                }
                incident.setIncidentRecord(existingIncident.getIncidentRecord());
                incident.setUpdatedAt(dateUtil.getTimestamp());
            }

            // Copy properties except createdBy, createdAt
            BeanUtils.copyProperties(incidentDTO, incident, "incidentStatusId", "createdBy", "createdAt");
            incident = incidentDetailsRepository.save(incident);
            Integer incidentId = incident.getIncidentId();


            // Handle File Upload if files are present
            handleFileUpload(files, incidentId, incidentDTO.getUserId());

            log.info("Incident saved: {}", incident);
            incidentFiles = storageService.getAllFileDetails(incidentId, "Incident");

            // Prepare the response based on whether it's a new or updated incident
            if (isNewAction) {
                handleNewIncidentResponse(incident, statusResponse);
                String title = incident.getTitle();

                int severityId = incidentDTO.getSeverityId();
                String severityValue = incidentDTO.getSeverity(); // Check the severity string

                MasterSourceResponse masterSourceResponse = getMastersListByType("Incident Severity");

                String severity = (severityValue == null)
                        ? masterSourceResponse.getMasterList().stream()
                        .filter(master -> master.getSourceId() == severityId) // Match the severityId
                        .map(MastersDTO::getSourceType) // Extract the sourceType
                        .findFirst() // Get the first match (Optional)
                        .orElse("Unknown") // Default value if no match is found
                        : severityValue;

                List<UsersByDepartment> usersByDepartments = userManagementService.getUsersByDepartment(incidentDTO.getOrgId(), incidentDTO.getDepartmentId());

                List<String> usernames = usersByDepartments.stream()
                        .map(UsersByDepartment::getEmailAddress) // Extract emailAddress from each UsersByDepartment
                        .toList();

                sendMail(incidentId, severity, title, usernames);
            } else {
                handleUpdatedIncidentResponse(incident, userId, incidentDTO, statusResponse);
            }

        } catch (Exception ex) {
            statusResponse.setResponseCode(500);
            statusResponse.setResponseMessage(INTERNAL_SERVER_ERROR);
            log.error("Exception in createIncidentByUser: {}", ex.getMessage());
        }

        return new AddIncidentResponse(statusResponse, incident, incidentFiles);
    }

    private boolean sendMail(int incidentId, String severity, String title, List<String> email) {
        String templatePath = "/templates/create-incident.ftl"; // Relative path to the template from the classpath
        boolean result = false;
        try {
            // Create FreeMarker Configuration
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_30);
            cfg.setClassForTemplateLoading(this.getClass(), "/"); // Set the classpath for template loading

            // Load the template
            Template template = cfg.getTemplate(templatePath);

            // Create the data model for template processing
            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("incidentId", incidentId);
            dataModel.put("severity", severity);
            dataModel.put("title", title);


            // Process the template with the data model
            StringWriter writer = new StringWriter();
            template.process(dataModel, writer);

            // Get the processed content as a String
            String content = writer.toString();

            // Send the email
            emailUtil.sendEmail(content, email, "Incident Creation");

            result = true;
        } catch (Exception ex) {
            log.error("Error in addNewUser in UserServiceImpl: {}", ex.getMessage());
        }
        return result;
    }

    // Helper method to process source, category, severity
    private void processMasterData(IncidentsDTO incidentDTO) {
        if (StringUtils.isNotEmpty(incidentDTO.getSource())) {
            RequestMasterSource sourceRequest = new RequestMasterSource();
            sourceRequest.setSourceName("Incident Source");
            sourceRequest.setSourceType(incidentDTO.getSource());
            AddMasterSourceResponse sourceResponse = addMasterListByType(sourceRequest);
            incidentDTO.setSourceId(sourceResponse.getMasterSource().getSourceId());
        }

        if (StringUtils.isNotEmpty(incidentDTO.getCategory())) {
            RequestMasterSource categoryRequest = new RequestMasterSource();
            categoryRequest.setSourceName("Incident Category");
            categoryRequest.setSourceType(incidentDTO.getCategory());
            AddMasterSourceResponse categoryResponse = addMasterListByType(categoryRequest);
            incidentDTO.setCategoryId(categoryResponse.getMasterSource().getSourceId());
        }

        if (StringUtils.isNotEmpty(incidentDTO.getSeverity())) {
            RequestMasterSource severityRequest = new RequestMasterSource();
            severityRequest.setSourceName("Incident Severity");
            severityRequest.setSourceType(incidentDTO.getSeverity());
            AddMasterSourceResponse severityResponse = addMasterListByType(severityRequest);
            incidentDTO.setSeverityId(severityResponse.getMasterSource().getSourceId());
        }

        if (StringUtils.isNotEmpty(incidentDTO.getDepartment())) {
            List<Department> departmentList = userService.getAllDepartments();

            // Check if department exists in the list
            Department existingDepartment = departmentList.stream()
                    .filter(dept -> dept.getDepartmentName().equalsIgnoreCase(incidentDTO.getDepartment()))
                    .findFirst()
                    .orElse(null);

            if (existingDepartment != null) {
                // Department exists, set department ID from existing entry
                incidentDTO.setDepartmentId(existingDepartment.getDepartmentID());
            } else {
                // Department does not exist, save and set department ID
                Department newDepartment = userService.saveDepartments(incidentDTO.getDepartment());
                incidentDTO.setDepartmentId(newDepartment.getDepartmentID());
            }
        }

    }

    // Helper method to handle file uploads
    private void handleFileUpload(List<MultipartFile> files, Integer incidentId, Integer userId) {
        if (files != null && !files.isEmpty()) {
            boolean hasValidFiles = false;
            for (MultipartFile file : files) {
                String originalFilename = file.getOriginalFilename();

                // Check if the filename is valid (non-empty and non-null)
                if (originalFilename != null && !originalFilename.trim().isEmpty()) {
                    hasValidFiles = true;
                    break;
                }
            }

            // Upload files if valid files exist
            if (hasValidFiles) {
                storageService.uploadFiles(files, incidentId, incidentId, "Incident", userId);
            } else {
                log.warn("No valid files to upload. All files have empty filenames.");
            }
        } else {
            log.info("No files provided for incident");
        }
    }


    private void setupNewIncident(Incidents incident, IncidentsDTO incidentsDTO, int userId) {
        incident.setIncidentStatusId(incidentsDTO.getIncidentStatusId());
        incident.setCreatedBy(userId);
        incident.setCreatedAt(dateUtil.getTimestamp());
    }

    private Incidents setupExistingIncident(IncidentsDTO incidentDTO, Incidents incident) {
        Incidents existingIncident = incidentDetailsRepository.findById(incidentDTO.getIncidentId()).orElse(null);
        if (existingIncident != null) {
            existingIncident.setIncidentStatusId(existingIncident.getIncidentStatusId());
            existingIncident.setCreatedBy(existingIncident.getCreatedBy());
            existingIncident.setCreatedAt(existingIncident.getCreatedAt());
        }
        return existingIncident;
    }

    private void handleNewIncidentResponse(Incidents incident, StatusResponse statusResponse) {
        if (incident.getIncidentId() != 0) {
            incidentDetailsRepository.updateUniqueIncidentRecord(incident.getCategoryId(), incident.getIncidentId());
            GetUserByIdResponse getUserByIdResponse = userService.getUserDetailsById(incident.getCreatedBy());
            String firstName = getUserByIdResponse.getUsers().getFirstName();
            String lastName = getUserByIdResponse.getUsers().getLastName();
            incidentHistoryRepo.saveIncidentHistory(incident.getIncidentId(), "Incident Creation", "Incident Created by " + firstName + " " + lastName, firstName + " " + lastName);
            statusResponse.setResponseCode(201);
            statusResponse.setResponseMessage("Incident Created Successfully");

        } else {
            statusResponse.setResponseCode(403);
            statusResponse.setResponseMessage("Failed to create incident");
        }
    }

    private void handleUpdatedIncidentResponse(Incidents incident, int userId, IncidentsDTO incidentDTO, StatusResponse statusResponse) {
        if (incident.getIncidentId().equals(incidentDTO.getIncidentId())) {
            GetUserByIdResponse getUserByIdResponse = userService.getUserDetailsById(userId);
            String firstName = getUserByIdResponse.getUsers().getFirstName();
            String lastName = getUserByIdResponse.getUsers().getLastName();
            incidentHistoryRepo.saveIncidentHistory(incident.getIncidentId(), "Incident Update", "Incident Updated by: " + firstName + " " + lastName, firstName + " " + lastName);
            statusResponse.setResponseCode(200);
            statusResponse.setResponseMessage("Incident updated Successfully");
        } else {
            statusResponse.setResponseCode(403);
            statusResponse.setResponseMessage("Failed to update incident");
        }
    }


    @Override
    public IncidentDetailsDashboardResponse fetchIncidentDetailsJson(Integer orgId, Integer incidentStatusId, Integer assignedUserId) {
        StatusResponse statusResponse = new StatusResponse();
        List<IncidentDashboard> returnList = new ArrayList<>();
        int totalRecords = 0;
        try {
            returnList = incidentDashboardRepo.getIncidentDetailsForDashboard(orgId, incidentStatusId, assignedUserId);
            if (!returnList.isEmpty()) {
                totalRecords = returnList.size();
                statusResponse.setResponseCode(200);
                statusResponse.setResponseMessage("List successfully fetched");
            } else {
                statusResponse.setResponseCode(400);
                statusResponse.setResponseMessage(AppConstants.NO_RECORDS);
            }
        } catch (Exception e) {
            log.error("Exception in fetchIncidentDetailsJson{}", e.getMessage());
            statusResponse.setResponseCode(500);
            statusResponse.setResponseMessage(INTERNAL_SERVER_ERROR);
        }
        return new IncidentDetailsDashboardResponse(statusResponse, totalRecords, returnList);
    }

    @Override
    public TaskMasterResponse addTasksWithDepartment(RequestTaskMaster requestTaskMaster) {
        log.info("In addTasksWithDepartment method start");
        StatusResponse statusResponse = new StatusResponse();
        String taskName = requestTaskMaster.getTaskName();
        try {
            boolean sourceTypeExists = taskMasterRepository.existsByTaskName(taskName);
            if (sourceTypeExists) {
                TaskMaster taskMaster = taskMasterRepository.findByTaskName(taskName);
                statusResponse.setResponseCode(400);
                statusResponse.setResponseMessage("Task Already Exists");
                return new TaskMasterResponse(statusResponse, taskMaster);
            }

            TaskMaster newTasks = new TaskMaster();
            newTasks.setDepartmentId(requestTaskMaster.getDepartmentId());
            newTasks.setTaskName(taskName);

            TaskMaster savedTask = taskMasterRepository.save(newTasks);
            statusResponse.setResponseMessage("Task Added Successfully");
            statusResponse.setResponseCode(200);
            return new TaskMasterResponse(statusResponse, savedTask);
        } catch (Exception e) {
            statusResponse.setResponseCode(500);
            statusResponse.setResponseMessage(INTERNAL_SERVER_ERROR);
            return new TaskMasterResponse(statusResponse, null);
        }
    }

    @Override
    public AddMasterSourceResponse addMasterListByType(RequestMasterSource requestMasterSource) {
        log.info("In addMasterListByType method start");
        StatusResponse statusResponse = new StatusResponse();
        try {
            // Check if the sourceType already exists
            boolean sourceTypeExists = masterSourceRepository.existsBySourceTypeAndIsActive(requestMasterSource.getSourceType(), 1);
            if (sourceTypeExists) {
                MasterSource masterSourceExists = masterSourceRepository.findBySourceTypeAndIsActive(requestMasterSource.getSourceType());
                statusResponse.setResponseCode(400);
                statusResponse.setResponseMessage("SourceType already exists");
                return new AddMasterSourceResponse(statusResponse, masterSourceExists);
            }

            // Find the highest serial number from the active sources
            List<MasterSource> masterSourceList = masterSourceRepository.findBySourceNameAndIsActive(requestMasterSource.getSourceName());
            Integer serialNumber = masterSourceList.stream()
                    .max(Comparator.comparingInt(MasterSource::getSNo))
                    .map(masterSource -> masterSource.getSNo() + 1)
                    .orElse(1);

            // Create a new MasterSource instance and set its properties
            MasterSource source = new MasterSource();
            source.setSourceName(requestMasterSource.getSourceName());
            source.setSourceType(requestMasterSource.getSourceType());
            source.setSNo(serialNumber);
            source.setIsActive(1);
            source.setCreatedAt(dateUtil.getTimestamp());

            // Save the new MasterSource instance
            MasterSource savedSource = masterSourceRepository.save(source);
            statusResponse.setResponseCode(200);
            statusResponse.setResponseMessage("Added successfully");
            return new AddMasterSourceResponse(statusResponse, savedSource);
        } catch (Exception e) {
            statusResponse.setResponseCode(500);
            statusResponse.setResponseMessage("Internal Server Error");
            log.error("Exception occurred in addMasterListByType", e);
            return new AddMasterSourceResponse(statusResponse, null);
        }
    }

    @Override
    public FetchTaskResponse fetchTasksByDepartment(int departmentId) {
        log.info("In fetchTasksByDepartment method start");
        StatusResponse statusResponse = new StatusResponse();
        List<TaskMaster> taskList = new ArrayList<>();
        try {
            taskList = taskMasterRepository.getTaskList(departmentId);
            if (!taskList.isEmpty()) {
                statusResponse.setResponseMessage("List Fetched Successfully");
                statusResponse.setResponseCode(200);

            } else {
                statusResponse.setResponseCode(400);
                statusResponse.setResponseMessage(NO_RECORDS);
            }
        } catch (Exception e) {
            statusResponse.setResponseMessage(INTERNAL_SERVER_ERROR);
            statusResponse.setResponseCode(500);
            log.error("Exception occurred in fetchTasksByDepartment {} ", e.getMessage());
        }
        return new FetchTaskResponse(statusResponse, taskList);
    }

    @Override
    public MasterSourceResponse getMastersListByType(String sourceType) {
        log.info("In getMastersListByType method start");
        StatusResponse statusResponse = new StatusResponse();
        List<MastersDTO> returnList = new ArrayList<>();
        try {
            List<MasterSource> list = masterSourceRepository.findBySourceNameAndIsActive(sourceType);
            if (list != null && !list.isEmpty()) {
                for (MasterSource source : list) {
                    MastersDTO dto = new MastersDTO();
                    dto.setSourceId(source.getSourceId());
                    dto.setSourceType(source.getSourceType()); // Corrected to set sourceName
                    returnList.add(dto);
                }
                statusResponse.setResponseCode(200);
                statusResponse.setResponseMessage("List Successfully Fetched");
                log.info("List Successfully Fetched");
            } else {
                statusResponse.setResponseCode(400);
                statusResponse.setResponseMessage(NO_RECORDS);
                log.info(NO_RECORDS);
            }
        } catch (Exception e) {
            log.error("Exception in getMastersListByType: {}", e.getMessage(), e);
            statusResponse.setResponseCode(500);
            statusResponse.setResponseMessage(INTERNAL_SERVER_ERROR);
        }
        log.info("In getMastersListByType method end");
        return new MasterSourceResponse(statusResponse, returnList);
    }

    @Override
    public PreventiveActionResponse savePreventiveActionResponse(PreventiveActionDTO preventiveActionDTO, List<MultipartFile> files) {
        StatusResponse statusResponse = new StatusResponse();
        IncidentPreventiveAction incidentPreventiveAction = new IncidentPreventiveAction();
        List<DocumentEntity> preventiveFiles = new ArrayList<>();

        if (preventiveActionDTO == null) {
            log.info(INVALID_PARAMETERS);
            statusResponse.setResponseMessage(INVALID_PARAMETERS);
            statusResponse.setResponseCode(400);
            return new PreventiveActionResponse(statusResponse, null, null);
        }

        try {
            String flag = preventiveActionDTO.getFlag();
            int incidentId = preventiveActionDTO.getIncidentId();
            int preventiveId = preventiveActionDTO.getPreventiveId();
            String findings = preventiveActionDTO.getFindings();
            int createdBy = preventiveActionDTO.getCreatedBy();
            incidentPreventiveAction = preventiveActionRepository.savePreventiveAction(flag, preventiveId, incidentId, findings, createdBy);
            if (files != null && !files.isEmpty()) {
                boolean hasValidFiles = false;
                for (MultipartFile file : files) {
                    String originalFilename = file.getOriginalFilename();

                    // Check if the filename is valid (non-empty and non-null)
                    if (originalFilename != null && !originalFilename.trim().isEmpty()) {
                        hasValidFiles = true;
                        break;  // As soon as a valid file is found, no need to check further
                    }
                }

                // Call the uploadFiles method if at least one valid file is found
                if (hasValidFiles) {
                    storageService.uploadFiles(files, incidentId, incidentPreventiveAction.getPreventiveId(), "PA", createdBy);
                } else {
                    log.warn("No valid files to upload. All files have empty filenames.");
                }
            }
            preventiveFiles = storageService.getAllFileDetails(incidentId, "PA");
            if (flag.equalsIgnoreCase("I")) {
                log.info("Prevention Action created Successfully");
                statusResponse.setResponseCode(201);
                statusResponse.setResponseMessage("Prevention Action created Successfully");

            } else if (flag.equalsIgnoreCase("U")) {
                log.info("Prevention Action updated Successfully");
                statusResponse.setResponseCode(200);
                statusResponse.setResponseMessage("Prevention Action updated Successfully");
            } else {
                log.info("Failed to create Prevention Action");
                statusResponse.setResponseCode(403);
                statusResponse.setResponseMessage("Failed to create Prevention Action");
            }
        } catch (Exception e) {
            log.error("Exception Occurred in savePreventiveActionResponse method: {}", e.getMessage());
            statusResponse.setResponseMessage("Internal Server Error");
            statusResponse.setResponseCode(500);
        }

        return new PreventiveActionResponse(statusResponse, incidentPreventiveAction, preventiveFiles);
    }

    @Override
    public RootCauseResponse saveRootCauseAction(RootCauseAnalysis rootCauseAnalysis, List<MultipartFile> files) {
        log.info("In saveRootCauseAction method starts");
        StatusResponse statusResponse = new StatusResponse();
        try {
            int userId = rootCauseAnalysis.getUserId();
            String flag = rootCauseAnalysis.getFlag();
            int incidentId = rootCauseAnalysis.getIncidentId();


            IncidentRootCause incidentRootCause = incidentRootCauseRepository.saveIncidentRootCause(flag, rootCauseAnalysis.getRootCauseId(), rootCauseAnalysis.getIncidentId(), rootCauseAnalysis.getProblemDescription(), rootCauseAnalysis.getProblemWhy(), rootCauseAnalysis.getRootCauseSummary(), userId);
            List<RootCauseWhy> whyDetails = rootCauseAnalysis.getProblems();
            List<ProblemRootCause> rootCause = new ArrayList<>();
            for (RootCauseWhy rootCauseWhy : whyDetails) {
                ProblemRootCause problemRootCause = getProblemRootCause(rootCauseAnalysis, rootCauseWhy, userId);
                problemRootCause = problemRootCauseRepository.save(problemRootCause);
                rootCause.add(problemRootCause);
            }
            if (files != null && !files.isEmpty()) {
                boolean hasValidFiles = false;
                for (MultipartFile file : files) {
                    String originalFilename = file.getOriginalFilename();

                    // Check if the filename is valid (non-empty and non-null)
                    if (originalFilename != null && !originalFilename.trim().isEmpty()) {
                        hasValidFiles = true;
                        break;  // As soon as a valid file is found, no need to check further
                    }
                }

                // Call the uploadFiles method if at least one valid file is found
                if (hasValidFiles) {
                    storageService.uploadFiles(files, rootCauseAnalysis.getIncidentId(), incidentRootCause.getRootCauseId(), "RootCause", userId);
                } else {
                    log.warn("No valid files to upload. All files have empty filenames.");
                }
            }
            if (flag.equalsIgnoreCase("I")) {
                log.info("Root Cause Action created Successfully");
                statusResponse.setResponseCode(201);
                statusResponse.setResponseMessage("Root Cause Action created Successfully");

            } else if (flag.equalsIgnoreCase("U")) {
                log.info("Root Cause Action updated Successfully");
                statusResponse.setResponseCode(200);
                statusResponse.setResponseMessage("Root Cause Action updated Successfully");
            } else {
                log.info("Failed to create Root Cause Action");
                statusResponse.setResponseCode(403);
                statusResponse.setResponseMessage("Failed to create Root Cause Action");
            }
            List<DocumentEntity> rootCauseFiles = storageService.getAllFileDetails(incidentId, "RootCause");

            return new RootCauseResponse(statusResponse, incidentRootCause, rootCause, rootCauseFiles);
        } catch (Exception e) {
            log.error("Exception occurred in saveRootCauseAction {} ", e.getMessage());
            statusResponse.setResponseCode(500);
            statusResponse.setResponseMessage(INTERNAL_SERVER_ERROR);
            return new RootCauseResponse(statusResponse, null, null, null);
        }
    }

    @Override
    public StatusResponse deleteProblemRootCause(ProblemRootCause problemRootCause) {
        StatusResponse statusResponse = new StatusResponse();
        try {
            problemRootCauseRepository.deleteProblemRootCause(problemRootCause.getProblemStageId(), problemRootCause.getIncidentId());
            statusResponse.setResponseCode(200);
            statusResponse.setResponseMessage("Deleted successfully");
        } catch (Exception e) {
            statusResponse.setResponseMessage(INTERNAL_SERVER_ERROR);
            statusResponse.setResponseCode(500);
            log.error("Exception occurred in deleteProblemRootCause {}", e.getMessage());
        }
        return statusResponse;
    }

    private ProblemRootCause getProblemRootCause(RootCauseAnalysis rootCauseAnalysis, RootCauseWhy rootCauseWhy, int userId) {
        ProblemRootCause problemRootCause = new ProblemRootCause();
        boolean isRootPresent = (rootCauseWhy.getProblemStatusId() == null || rootCauseWhy.getProblemStatusId() == 0);
        if (!isRootPresent) {
            problemRootCause.setProblemStageId(rootCauseWhy.getProblemStatusId());
        }
        problemRootCause.setIncidentId(rootCauseAnalysis.getIncidentId());
        problemRootCause.setStage(rootCauseWhy.getStage());
        problemRootCause.setStageNumber(rootCauseWhy.getStageNumber());
        problemRootCause.setOccur(rootCauseWhy.getOccur());
        problemRootCause.setPrevented(rootCauseWhy.getPrevented());
        problemRootCause.setUndetected(rootCauseWhy.getUndetected());
        problemRootCause.setCreatedBy(userId);
        problemRootCause.setCreatedAt(dateUtil.getTimestamp());
        return problemRootCause;
    }

    @Override
    public SaveInterimResponse saveIncidentInterim(RequestIncidentInterim requestIncidentInterim, List<MultipartFile> files) {
        log.info("In saveIncidentInterim method starts");
        StatusResponse statusResponse = new StatusResponse();
        IncidentInterim incidentInterim = new IncidentInterim();
        List<DocumentEntity> interimFiles = new ArrayList<>();
        try {
            String flag = requestIncidentInterim.getFlag();
            int interimId = requestIncidentInterim.getInterimId();
            int incidentId = requestIncidentInterim.getIncidentId();
            String findings = requestIncidentInterim.getFindings();
            int createdBy = requestIncidentInterim.getCreatedBy();

            incidentInterim = incidentInterimRepo.saveInterimDetails(flag, interimId, incidentId, findings, createdBy);
            if (files != null && !files.isEmpty()) {
                boolean hasValidFiles = false;
                for (MultipartFile file : files) {
                    String originalFilename = file.getOriginalFilename();

                    // Check if the filename is valid (non-empty and non-null)
                    if (originalFilename != null && !originalFilename.trim().isEmpty()) {
                        hasValidFiles = true;
                        break;  // As soon as a valid file is found, no need to check further
                    }
                }

                // Call the uploadFiles method if at least one valid file is found
                if (hasValidFiles) {
                    storageService.uploadFiles(files, incidentId, incidentInterim.getInterimId(), "Interim", createdBy);
                } else {
                    log.warn("No valid files to upload. All files have empty filenames.");
                }
            }

            if ("I".equalsIgnoreCase(requestIncidentInterim.getFlag())) {
                statusResponse.setResponseCode(201);
                statusResponse.setResponseMessage("Interim Created Successfully");
            } else if ("U".equalsIgnoreCase(requestIncidentInterim.getFlag())) {
                log.info("Interim updated Successfully");
                statusResponse.setResponseCode(200);
                statusResponse.setResponseMessage("Interim  updated Successfully");
            } else {
                log.info("Failed to create Interim Action");
                statusResponse.setResponseCode(403);
                statusResponse.setResponseMessage("Failed to create Interim Action");
            }
            interimFiles = storageService.getAllFileDetails(incidentId, "Interim");


        } catch (Exception e) {
            log.error("Exception occurred in saveIncidentInterim method {}", e.getMessage());
            statusResponse.setResponseCode(500);
            statusResponse.setResponseMessage(INTERNAL_SERVER_ERROR);
        }
        return new SaveInterimResponse(statusResponse, incidentInterim, interimFiles);
    }

    @Override
    public CorrectiveActionResponse saveCorrectiveAction(CorrectiveActionPlan correctiveActionPlan, List<MultipartFile> files) {
        log.info("In saveCorrectiveAction method starts");
        StatusResponse statusResponse = new StatusResponse();
        CorrectiveActionResponse correctiveActionResponse = new CorrectiveActionResponse();
        String flag = correctiveActionPlan.getFlag();
        try {

            IncidentCorrectiveActionPlan incidentCorrectiveActionPlan = getIncidentCorrectiveActionPlan(correctiveActionPlan);
            incidentCorrectiveActionPlan = correctiveActionPlanMasterRepository.save(incidentCorrectiveActionPlan);
            if (files != null && !files.isEmpty()) {
                boolean hasValidFiles = false;
                for (MultipartFile file : files) {
                    String originalFilename = file.getOriginalFilename();

                    // Check if the filename is valid (non-empty and non-null)
                    if (originalFilename != null && !originalFilename.trim().isEmpty()) {
                        hasValidFiles = true;
                        break;  // As soon as a valid file is found, no need to check further
                    }
                }

                // Call the uploadFiles method if at least one valid file is found
                if (hasValidFiles) {
                    storageService.uploadFiles(files, incidentCorrectiveActionPlan.getIncidentId(), incidentCorrectiveActionPlan.getIncidentActionPlanId(), "CAP", correctiveActionPlan.getUserId());
                } else {
                    log.warn("No valid files to upload. All files have empty filenames.");
                }
            }
            log.info("Corrective Action Plan Created Successfully");

            if ("I".equalsIgnoreCase(flag)) {
                statusResponse.setResponseMessage("Corrective Action Plan Created Successfully");
                statusResponse.setResponseCode(201);
            } else if ("U".equalsIgnoreCase(flag)) {
                statusResponse.setResponseMessage("Corrective Action Plan Updated Successfully");
                statusResponse.setResponseCode(200);
            } else {
                statusResponse.setResponseCode(403);
                statusResponse.setResponseMessage("Corrective Action Plan Creation Failed");
            }
            List<DocumentEntity> getFiles = storageService.getAllFileDetails(incidentCorrectiveActionPlan.getIncidentId(), "CAP");


            correctiveActionResponse.setStatusResponse(statusResponse);
            correctiveActionResponse.setIncidentCorrectiveActionPlanDetails(incidentCorrectiveActionPlan);
            correctiveActionResponse.setCorrectiveFiles(getFiles);
        } catch (Exception e) {
            log.error("Error in saveCorrectiveAction method: ", e);
            statusResponse.setResponseMessage("Failure");
            statusResponse.setResponseCode(500);
            correctiveActionResponse.setStatusResponse(statusResponse);
        }

        log.info("In saveCorrectiveAction method ends");
        return correctiveActionResponse;
    }

    @Override
    public GetTasksResponse saveTasksForCap(RequestCAPTask requestCAPTask, List<MultipartFile> files) {
        StatusResponse statusResponse = new StatusResponse();
        List<DocumentEntity> taskFile = new ArrayList<>();
        CAPTaskMaster capTaskMaster = new CAPTaskMaster();
        try {
            String flag = requestCAPTask.getFlag();
            int userId = requestCAPTask.getCreatedBy();
            String taskName = requestCAPTask.getTaskName();
            if (StringUtils.isNotEmpty(taskName)) {

                boolean sourceTypeExists = taskMasterRepository.existsByTaskName(taskName);
                if (sourceTypeExists) {
                    TaskMaster taskMaster = taskMasterRepository.findByTaskName(taskName);
                    requestCAPTask.setTaskId(taskMaster.getTaskId());
                } else {
                    RequestTaskMaster taskMaster = new RequestTaskMaster();
                    taskMaster.setDepartmentId(requestCAPTask.getDepartmentId());
                    taskMaster.setTaskName(taskName);
                    TaskMasterResponse sourceResponse = addTasksWithDepartment(taskMaster);
                    requestCAPTask.setTaskId(sourceResponse.getTasks().getTaskId());
                }
            }
            capTaskMaster = capTaskRepository.saveCapTasks(flag, requestCAPTask.getCapTaskId(), requestCAPTask.getIncidentId(), requestCAPTask.getTaskId(), requestCAPTask.getComments(), requestCAPTask.getDueDate(), requestCAPTask.getResolvedFlag(), userId);
            if (files != null && !files.isEmpty()) {
                boolean hasValidFiles = false;
                for (MultipartFile file : files) {
                    String originalFilename = file.getOriginalFilename();

                    // Check if the filename is valid (non-empty and non-null)
                    if (originalFilename != null && !originalFilename.trim().isEmpty()) {
                        hasValidFiles = true;
                        break;  // As soon as a valid file is found, no need to check further
                    }
                }

                // Call the uploadFiles method if at least one valid file is found
                if (hasValidFiles) {
                    storageService.uploadFiles(files, capTaskMaster.getIncidentId(), capTaskMaster.getCapTaskId(), "Tasks", capTaskMaster.getCreatedBy());
                } else {
                    log.warn("No valid files to upload. All files have empty filenames.");
                }
            }
            if ("I".equalsIgnoreCase(flag)) {
                statusResponse.setResponseMessage("Corrective Action Plan Task Created Successfully");
                statusResponse.setResponseCode(201);
            } else if ("U".equalsIgnoreCase(flag)) {
                statusResponse.setResponseMessage("Corrective Action Plan Task Updated Successfully");
                statusResponse.setResponseCode(200);
            } else if ("D".equalsIgnoreCase(flag)) {
                statusResponse.setResponseMessage("Corrective Action Plan Task Deleted Successfully");
                statusResponse.setResponseCode(203);
            } else {
                statusResponse.setResponseCode(403);
                statusResponse.setResponseMessage("Corrective Action Plan Task Creation Failed");
            }
            taskFile = storageService.getAllFileDetailsForTask(capTaskMaster.getIncidentId(), capTaskMaster.getCapTaskId(), "Tasks");

        } catch (Exception e) {
            statusResponse.setResponseCode(500);
            statusResponse.setResponseMessage(INTERNAL_SERVER_ERROR);
        }
        return new GetTasksResponse(statusResponse, capTaskMaster, taskFile);
    }

    @Override
    public IncidentChatResponse saveIncidentChat(RequestChatDto requestChatDto) {
        log.info("In saveIncidentChat service");
        StatusResponse statusResponse = new StatusResponse();
        IncidentChat incidentChat = new IncidentChat();

        try {
            // Set incident chat details from request DTO
            incidentChat.setComments(requestChatDto.getComments());
            incidentChat.setIncidentId(requestChatDto.getIncidentId());
            incidentChat.setUserId(requestChatDto.getUserId());
            incidentChat.setCreatedOn(dateUtil.getTimestamp().toString());

            // Save incident chat
            incidentChat = incidentChatRepository.save(incidentChat);

            // Check if save was successful
            statusResponse.setResponseMessage("Chat Saved Successfully");
            statusResponse.setResponseCode(200);
            return new IncidentChatResponse(statusResponse, null, incidentChat);
        } catch (Exception e) {
            log.error("Error in saveIncidentChat: ", e);
            statusResponse.setResponseMessage("Internal Server Error");
            statusResponse.setResponseCode(500);
            return new IncidentChatResponse(statusResponse, null, incidentChat);
        }
    }

    @Override
    public IncidentChatResponse getChatByIncidentId(RequestChatDto requestChatDto) {
        log.error("In getChatByIncidentId method");
        StatusResponse statusResponse = new StatusResponse();
        List<GetIncidentChatDetails> incidentChats = new ArrayList<>();
        try {
            incidentChats = getIncidentChatRepo.getIncidentChatByIncidentId(requestChatDto.getIncidentId());
            if (!incidentChats.isEmpty()) {
                statusResponse.setResponseCode(200);
                statusResponse.setResponseMessage("Chats Received");
            } else {
                statusResponse.setResponseMessage("No chats");
                statusResponse.setResponseCode(400);
            }
        } catch (Exception e) {
            log.error("Exception occurred in getChatByIncidentId {}", e.getMessage());
            statusResponse.setResponseCode(500);
            statusResponse.setResponseMessage(INTERNAL_SERVER_ERROR);
        }
        return new IncidentChatResponse(statusResponse, incidentChats, null);
    }

    @Override
    public GetIncidentAssignResponse getIncidentAssignDetails(RequestIncidentAssign requestIncidentAssign) {
        StatusResponse statusResponse = new StatusResponse();
        GetIncidentAssign incidentAssign = new GetIncidentAssign();
        try {
            incidentAssign = getIncidentAssignRepository.getAssign(requestIncidentAssign.getIncidentId());
            if (incidentAssign != null) {
                statusResponse.setResponseCode(200);
                statusResponse.setResponseMessage("Incident Assign Details Fetched Successfully");
            } else {
                statusResponse.setResponseMessage("No Incident Assign Records");
                statusResponse.setResponseCode(400);
            }
        } catch (Exception e) {
            log.error("Exception occurred while fetching getIncidentAssignDetails {} ", e.getMessage());
            statusResponse.setResponseCode(500);
            statusResponse.setResponseMessage(INTERNAL_SERVER_ERROR);
        }
        return new GetIncidentAssignResponse(statusResponse, incidentAssign);
    }

    @Override
    public StatusResponse closeIncident(RequestChatDto requestChatDto) {
        StatusResponse statusResponse = new StatusResponse();

        try {
            // Fetch the current status of the incident
            Optional<Incidents> incidentDetails = incidentDetailsRepository.findById(requestChatDto.getIncidentId());

            // Check if the incident exists
            if (incidentDetails.isPresent()) {
                Incidents incident = incidentDetails.get();

                // Check if the incident is already closed
                if (incident.getIncidentStatusId() == 33) {
                    statusResponse.setResponseMessage("Incident is already closed");
                    statusResponse.setResponseCode(400);
                } else {
                    // Close the incident
                    incidentDetailsRepository.closeIncident(requestChatDto.getIncidentId(), requestChatDto.getUserId());
                    statusResponse.setResponseMessage("Incident Closed Successfully");
                    statusResponse.setResponseCode(200);
                }
            } else {
                // Incident not found
                statusResponse.setResponseMessage("Incident not found");
                statusResponse.setResponseCode(404);
            }
        } catch (Exception e) {
            statusResponse.setResponseCode(500);
            statusResponse.setResponseMessage("Internal Server Error");
            log.error("Error occurred while closing incident: {}", e.getMessage());
        }

        return statusResponse;
    }

    @Override
    public NotificationResponse getNotifications() {
        StatusResponse statusResponse = new StatusResponse();
        int count = 0;
        List<IncidentNotification> notificationList = new ArrayList<>();
        try {
            notificationList = incidentNotificationRepo.getAllNotifications();
            count = notificationList.size();
            if (!notificationList.isEmpty()) {
                statusResponse.setResponseMessage("Notifications Fetched successfully");
                statusResponse.setResponseCode(200);
            } else {
                statusResponse.setResponseMessage("No notifications");
                statusResponse.setResponseCode(400);
            }
        } catch (Exception e) {
            log.error("Exception occurred in getNotifications: {} ", e.getMessage());
            statusResponse.setResponseCode(500);
            statusResponse.setResponseMessage(INTERNAL_SERVER_ERROR);
        }
        return new NotificationResponse(statusResponse, count, notificationList);
    }

    @Override
    public StatusResponse deleteField(int id) {
        StatusResponse statusResponse = new StatusResponse();
        try {
            fieldsRepository.deleteField(id);
            statusResponse.setResponseCode(200);
            statusResponse.setResponseMessage("Successfully Deleted");
        } catch (Exception e) {
            log.error("Exception occurred in deleteField {} ", e.getMessage());
            statusResponse.setResponseMessage(INTERNAL_SERVER_ERROR);
            statusResponse.setResponseCode(500);
        }
        return statusResponse;
    }

    @Override
    public StatusResponse deleteIncidentField(int id, int fieldId) {
        StatusResponse statusResponse = new StatusResponse();
        try {
            incidentFieldRepo.deleteIncidentField(id, fieldId);
            statusResponse.setResponseCode(200);
            statusResponse.setResponseMessage("Successfully deleted");
        } catch (Exception e) {
            log.error("Exception occurred in deleteIncidentField {} ", e.getMessage());
            statusResponse.setResponseMessage(INTERNAL_SERVER_ERROR);
            statusResponse.setResponseCode(500);
        }
        return statusResponse;
    }

    @Override
    public FieldsResponse getAllFields() {
        StatusResponse statusResponse = new StatusResponse();
        List<Fields> fields = new ArrayList<>();
        try {
            fields = fieldsRepository.getAllFields();
            if (!fields.isEmpty()) {
                statusResponse.setResponseMessage("Successfully Fetched");
                statusResponse.setResponseCode(200);
            } else {
                statusResponse.setResponseCode(400);
                statusResponse.setResponseMessage("No Records");
            }
        } catch (Exception e) {
            log.error("Exception occurred in getAllFields {} ", e.getMessage());
            statusResponse.setResponseMessage(INTERNAL_SERVER_ERROR);
            statusResponse.setResponseCode(500);
        }
        return new FieldsResponse(statusResponse, fields);
    }

    @Override
    public Fields saveField(Fields field) {
        field.setIsActive(1);
        return fieldsRepository.save(field);
    }

    @Override
    public List<IncidentFieldsDTO> getFieldsByIncidentId(Integer incidentId) {
        List<Object[]> rawResults = incidentFieldRepo.findFieldsByIncidentId(incidentId);
        List<IncidentFieldsDTO> fieldsList = new ArrayList<>();

        for (Object[] row : rawResults) {
            IncidentFieldsDTO dto = new IncidentFieldsDTO();
            dto.setFieldId((Integer) row[0]);
            dto.setIncidentFieldId((Integer) row[1]);
            dto.setType((String) row[2]);
            dto.setLabel((String) row[3]);

            // Convert JSON strings to JSON if needed
            try {
                dto.setOptions((String) row[4]); // Store as String if already JSON
                dto.setSelectedOption((String) row[5]); // Store as String if already JSON
            } catch (Exception e) {
                e.printStackTrace();
                // Handle JSON parsing exception if necessary
            }

            fieldsList.add(dto);
        }
        return fieldsList;
    }


    @Override
    public IncidentAssignResponse assignIncident(RequestIncidentAssign requestIncidentAssign) {
        StatusResponse statusResponse = new StatusResponse();
        String flag = requestIncidentAssign.getFlag();
        IncidentAssign incidentAssign = new IncidentAssign();
        try {

            incidentAssign = incidentAssignRepository.saveIncidentAssign(flag, requestIncidentAssign.getAssigningId(), requestIncidentAssign.getIncidentId(), requestIncidentAssign.getAssignTypeId(), requestIncidentAssign.getAssignTo(), requestIncidentAssign.getManagerId(), requestIncidentAssign.getRedoFlag(), requestIncidentAssign.getComments());
            if ("I".equalsIgnoreCase(flag)) {
                incidentAssignRepository.changeToInProgress(incidentAssign.getIncidentID());
                statusResponse.setResponseCode(201);
                statusResponse.setResponseMessage("Incident Assigned Successfully");
            } else if ("U".equalsIgnoreCase(flag)) {
                statusResponse.setResponseCode(200);
                statusResponse.setResponseMessage("Incident Assigned Updated Successfully");
            }
        } catch (Exception e) {
            statusResponse.setResponseCode(500);
            statusResponse.setResponseMessage(INTERNAL_SERVER_ERROR);
        }
        return new IncidentAssignResponse(statusResponse, incidentAssign);
    }

    @Override
    public ResponseEntity<InputStreamResource> getReport(int incidentId, int orgId, int userId) {

        try {
            // Parse the JSON input
            IncidentByIdResponse incidentByIdResponse = getIncidentDetailsById(incidentId, orgId, userId);
            RequestIncidentDetailsById requestBody = new RequestIncidentDetailsById();
            requestBody.setIncidentId(incidentId);
            requestBody.setUserId(userId);
            requestBody.setOrgId(orgId);
            GetIncidentTasksResponse getIncidentTasksResponse = getTaskDetails(requestBody);

            SaveInterimResponse saveInterimResponse = getInterimDetails(requestBody);

            GetRootCauseResponse getRootCauseResponse = getRootCauseDetails(requestBody);

            // Generate PDF Report
            InputStream pdfStream = ReportUtil.generatePdf(incidentByIdResponse.getIncidentDetails());

            // Return the generated PDF as a downloadable resource
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Incident_Report.pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(pdfStream));

        } catch (Exception e) {
            log.error("Error occurred while generating the report: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }


    /*public void saveFilesRelatedData(int incidentId, MultipartFile[] files,IncidentsDTO incidentDTO) {

        List<Integer> documentIdList = new ArrayList<>();
        try {

            // Save Folder Details into Database
            int userId = userUtil.getUserId();
            OrganizationDTO org = new OrganizationDTO();
            org.setOrgId(2);

            Repository repo = new Repository();
            repo.setOrganizationId(2);
            repo.setI
            RepositoryDTO repoDTO = new RepositoryDTO();
            repoDTO.setOrgDetails(org);
            repoDTO.setName(request.getParameter("name"));
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(userId);
            repoDTO.setUserDetails(userDTO);
            repoDTO.setLocation(incidentDirectory);

            List<RepositoryDTO> repositoryDTOList = new ArrayList<>();
            repositoryDTOList = repositoryService.getRepositaryDetailsByDirectoryName(incidentDirectory);
            if (repositoryDTOList != null && repositoryDTOList.size() > 0) {

                repoDTO.setId(repositoryDTOList.get(0).getId());
                documentIdList = documentsService.saveFilesToTRepo(request.getParameter("fileNames"),
                        request.getParameter("folderDirectory"), files, repoDTO);

                if (documentIdList != null && documentIdList.size() > 0) {
                    int batchId = (documentMappingDetailsRepository.getMaxBatchId() + 1);
                    Incidents incidents = incidentsReposiatry.findByPkIncidentId(incidentId);
                    if (incidents.getPkIncidentId() != 0) {
                        incidents.setDocumentBatchId(batchId);
                        incidentsReposiatry.save(incidents);
                        documentsService.saveDocumentMappingDetails(documentIdList, batchId, userId);
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}