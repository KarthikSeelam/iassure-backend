package com.iassure.incident.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iassure.incident.dto.*;
import com.iassure.incident.entity.DocumentEntity;
import com.iassure.incident.entity.Fields;
import com.iassure.incident.entity.IncidentField;
import com.iassure.incident.entity.ProblemRootCause;
import com.iassure.incident.repository.IncidentFieldRepo;
import com.iassure.incident.response.*;
import com.iassure.incident.service.*;
import com.iassure.util.OpenAIUtil;
import com.iassure.util.PdfUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Karthik S
 */
@RestController
@RequestMapping("/api/incident")
@Log4j2
@CrossOrigin
public class IncidentController {

    @Autowired
    IncidentService incidentService;

    @Autowired
    StorageService storageService;

    @Autowired
    IncidentFieldRepo incidentFieldRepo;

    @Autowired
    OpenAIUtil openAIUtil;

    @Autowired
    FileSplit fileSplit;

    @Autowired
    PdfUtil pdfUtil;

    @Autowired
    FileUploadService fileUploadService;

    @Autowired
    SearchService searchService;

    @PostMapping("/getIncidentDetailsById")
    public ResponseEntity<IncidentByIdResponse> getIncidentDetailsById(@RequestBody RequestIncidentDetailsById requestIncidentDetailsById) {
        log.info("In getIncidentDetailsById controller");
        try {
            IncidentByIdResponse result = incidentService.getIncidentDetailsById(requestIncidentDetailsById.getIncidentId(), requestIncidentDetailsById.getOrgId(), requestIncidentDetailsById.getUserId());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception occurred in getIncidentDetailsById controller {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/getPreventiveActionDetails")
    public ResponseEntity<GetPreventiveActionResponse> getPreventiveActionDetails(@RequestBody RequestIncidentDetailsById requestIncidentDetailsById) {
        log.info("In getIncidentDetailsById controller");
        try {
            GetPreventiveActionResponse result = incidentService.getPreventiveActionDetails(requestIncidentDetailsById.getOrgId(), requestIncidentDetailsById.getIncidentId(), requestIncidentDetailsById.getUserId());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception occurred in getIncidentDetailsById controller {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/getIncidentInterimDetails")
    public ResponseEntity<SaveInterimResponse> getIncidentInterimDetails(@RequestBody RequestIncidentDetailsById requestIncidentDetailsById) {
        log.info("In getIncidentInterimDetails controller");
        try {
            SaveInterimResponse interimResponse = incidentService.getInterimDetails(requestIncidentDetailsById);
            return new ResponseEntity<>(interimResponse, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception occurred in getIncidentInterimDetails controller {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/saveTasksForDepartment")
    public ResponseEntity<TaskMasterResponse> saveTasksForDepartment(@RequestBody RequestTaskMaster requestTaskMaster) {
        TaskMasterResponse taskMasterResponse = new TaskMasterResponse();
        try {
            taskMasterResponse = incidentService.addTasksWithDepartment(requestTaskMaster);
            return new ResponseEntity<>(taskMasterResponse, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(taskMasterResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/getTasksByDepartment")
    public ResponseEntity<FetchTaskResponse> getTasksByDepartment(@RequestBody RequestTaskMaster requestTaskMaster) {
        FetchTaskResponse fetchTaskResponse = new FetchTaskResponse();
        try {
            fetchTaskResponse = incidentService.fetchTasksByDepartment(requestTaskMaster.getDepartmentId());
            return new ResponseEntity<>(fetchTaskResponse, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(fetchTaskResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/getIncidentCAPDetails")
    public ResponseEntity<GetCapDetailsResponse> getIncidentCAPDetails(@RequestBody RequestIncidentDetailsById requestIncidentDetailsById) {
        GetCapDetailsResponse getCapDetailsResponse = new GetCapDetailsResponse();
        try {
            getCapDetailsResponse = incidentService.getCapDetails(requestIncidentDetailsById);
            return new ResponseEntity<>(getCapDetailsResponse, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception occurred in getIncidentCAPDetails {} ", e.getMessage());
            return new ResponseEntity<>(getCapDetailsResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/getTasksForIncident")
    public ResponseEntity<GetIncidentTasksResponse> getIncidentTaskDetails(@RequestBody RequestIncidentDetailsById requestIncidentDetailsById) {
        GetIncidentTasksResponse getIncidentTasksResponse = new GetIncidentTasksResponse();
        try {
            getIncidentTasksResponse = incidentService.getTaskDetails(requestIncidentDetailsById);
            return new ResponseEntity<>(getIncidentTasksResponse, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception occurred in getTasksForIncident {} ", e.getMessage());
            return new ResponseEntity<>(getIncidentTasksResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/getIncidentHistory")
    public ResponseEntity<GetHistoryResponse> getIncidentHistory(@RequestBody RequestIncidentDetailsById requestIncidentDetailsById) {
        log.info("In getIncidentHistory controller");
        GetHistoryResponse getHistoryResponse = new GetHistoryResponse();
        try {
            getHistoryResponse = incidentService.getIncidentHistory(requestIncidentDetailsById.getIncidentId());
            return new ResponseEntity<>(getHistoryResponse, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(getHistoryResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/getRootCauseDetails")
    public ResponseEntity<GetRootCauseResponse> getRootCauseDetails(@RequestBody RequestIncidentDetailsById requestIncidentDetailsById) {
        GetRootCauseResponse getRootCauseResponse = new GetRootCauseResponse();
        try {
            getRootCauseResponse = incidentService.getRootCauseDetails(requestIncidentDetailsById);
            return new ResponseEntity<>(getRootCauseResponse, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception occurred in getRootCauseDetails {} ", e.getMessage());
            return new ResponseEntity<>(getRootCauseResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/getIncidentCountDetails")
    public ResponseEntity<IncidentCountResponse> getIncidentCountDetails(@RequestBody RequestBodyDTO requestBodyDTO) {
        try {
            IncidentCountResponse incidentCountDetails = incidentService.getIncidentCountDetails(requestBodyDTO.getOrgId(), requestBodyDTO.getUserId());
            return new ResponseEntity<>(incidentCountDetails, incidentCountDetails.getIncidentCount() != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Exception occurred in getIncidentCountDetails: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping(value = "/addIncident")
    public ResponseEntity<AddIncidentResponse> addIncident(@RequestPart("incident") String incident, @RequestPart(value = "files", required = false) List<MultipartFile> files) throws JsonProcessingException {
        log.info(incident);
        IncidentsDTO incidentsDTO;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        incidentsDTO = objectMapper.readValue(incident, IncidentsDTO.class);
        log.info(incidentsDTO);
        AddIncidentResponse statusResponse = new AddIncidentResponse();
        try {
            statusResponse = incidentService.createIncidentByUser(incidentsDTO, files);
            return new ResponseEntity<>(statusResponse, HttpStatus.OK);

        } catch (Exception e) {
            log.error("Exception occured in the addIncident{}", e.getMessage());
            return new ResponseEntity<>(statusResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/deleteProblemRootCause")
    public ResponseEntity<StatusResponse> deleteProblemRootCause(@RequestBody ProblemRootCause problemRootCause) {
        try {
            StatusResponse statusResponse = incidentService.deleteProblemRootCause(problemRootCause);
            return new ResponseEntity<>(statusResponse, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception occurred in deleteProblemRootCause {} ", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addIncidentWithAI")
    public ResponseEntity<String> addIncidentWithAI(@RequestBody IncidentsDTO incidentsDTO) {
        String json = null;
        try {
            json = incidentService.createIncidentWithAI(incidentsDTO);
        } catch (Exception e) {
            log.error("Exception occurred in addIncidentWithAI controller");
        }
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @PostMapping("/addTasksWithAI")
    public ResponseEntity<String> addTasksWithAI(@RequestBody IncidentsDTO incidentsDTO) {
        String json = null;
        try {
            json = incidentService.createTasksWithAI(incidentsDTO);
        } catch (Exception e) {
            log.error("Exception occurred in addIncidentWithAI controller {}", e.getMessage());
        }
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @PostMapping("/addDashboardWithAI")
    public ResponseEntity<String> addDashboardWithAI(@RequestBody IncidentsDTO incidentsDTO) {
        String json = null;
        try {
            json = incidentService.createDashboardAI(incidentsDTO);
        } catch (Exception e) {
            log.error("Exception occurred in addDashboardWithAI controller {}", e.getMessage());
        }
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @PostMapping("/getSuggestions")
    public ResponseEntity<List<String>> getSuggestions(@RequestBody IncidentsDTO incidentsDTO) {
        List<String> json = new ArrayList<>();
        try {
            json = incidentService.getSuggestions(incidentsDTO);
        } catch (Exception e) {
            log.error("Exception occurred in addIncidentWithAI controller {}", e.getMessage());
        }
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @PostMapping("/closeIncident")
    public ResponseEntity<StatusResponse> closeIncident(@RequestBody RequestChatDto requestChatDto) {
        try {
            StatusResponse statusResponse = incidentService.closeIncident(requestChatDto);
            return new ResponseEntity<>(statusResponse, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception occurred in close Incident controller {} ", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @PostMapping("/fetchIncidentDetailsDashboard")
    public ResponseEntity<IncidentDetailsDashboardResponse> fetchIncidentDetailsJson(@RequestBody RequestBodyDTO requestBodyDTO) {
        IncidentDetailsDashboardResponse incidentDetailsDashboardResponse = new IncidentDetailsDashboardResponse();
        try {
            incidentDetailsDashboardResponse = incidentService.fetchIncidentDetailsJson(requestBodyDTO.getOrgId(), requestBodyDTO.getIncidentStatusId(), requestBodyDTO.getUserId());
            return new ResponseEntity<>(
                    incidentDetailsDashboardResponse,
                    incidentDetailsDashboardResponse.getDashboardList().isEmpty() ? HttpStatus.BAD_REQUEST : HttpStatus.OK
            );
        } catch (Exception e) {
            log.error("Exception occured in the fetchIncidentDetailsJson{}", e.getMessage());
            return new ResponseEntity<>(incidentDetailsDashboardResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addMasterByType")
    public ResponseEntity<AddMasterSourceResponse> addMasterByType(@RequestBody RequestMasterSource requestMasterSource) {
        log.info("In addMasterByType controller");
        AddMasterSourceResponse statusResponse = new AddMasterSourceResponse();
        try {
            statusResponse = incidentService.addMasterListByType(requestMasterSource);
            return new ResponseEntity<>(statusResponse, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception Occurred in addMasterByType {} ", e.getMessage());
            return new ResponseEntity<>(statusResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/getMastersListByType")
    public ResponseEntity<MasterSourceResponse> getMastersListByType(@RequestBody RequestBodyDTO requestBodyDTO) {
        log.info("In getMastersListByType controller");
        MasterSourceResponse masterSourceResponse = new MasterSourceResponse();
        try {
            masterSourceResponse = incidentService.getMastersListByType(requestBodyDTO.getSourceName());
            return new ResponseEntity<>(
                    masterSourceResponse,
                    masterSourceResponse.getMasterList().isEmpty() ? HttpStatus.BAD_REQUEST : HttpStatus.OK
            );
        } catch (Exception e) {
            log.error("Exception occured in the getMastersListByType{}", e.getMessage());
            return new ResponseEntity<>(masterSourceResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/getIncidentAssignDetails")
    public ResponseEntity<GetIncidentAssignResponse> getIncidentAssignDetails(@RequestBody RequestIncidentAssign requestIncidentAssign) {
        log.info("In getIncidentAssignDetails controller");
        GetIncidentAssignResponse getIncidentAssignResponse = new GetIncidentAssignResponse();
        try {
            getIncidentAssignResponse = incidentService.getIncidentAssignDetails(requestIncidentAssign);
            return new ResponseEntity<>(getIncidentAssignResponse, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception occurred in getIncidentAssignDetails controller {}", e.getMessage());
            return new ResponseEntity<>(getIncidentAssignResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/getNotifications")
    public ResponseEntity<NotificationResponse> getNotifications() {
        log.info("In getNotifications controller");
        try {
            NotificationResponse notificationResponse = incidentService.getNotifications();
            return new ResponseEntity<>(notificationResponse, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception occurred in getNotifications controller {} ", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/saveCorrectiveAction")
    public ResponseEntity<CorrectiveActionResponse> saveCorrectiveAction(@RequestPart("corrective") String requestCorrective, @RequestPart(value = "files", required = false) List<MultipartFile> files) throws JsonProcessingException {
        log.info("In saveCorrectiveAction controller");
        CorrectiveActionResponse correctiveActionResponse = new CorrectiveActionResponse();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        CorrectiveActionPlan incidentCorrectiveActionPlan = objectMapper.readValue(requestCorrective, CorrectiveActionPlan.class);

        try {
            correctiveActionResponse = incidentService.saveCorrectiveAction(incidentCorrectiveActionPlan, files);
            return new ResponseEntity<>(correctiveActionResponse, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception occurred in saveCorrectiveAction {} ", e.getMessage());
            return new ResponseEntity<>(correctiveActionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/saveTasksForCap")
    public ResponseEntity<GetTasksResponse> saveTasks(@RequestPart("tasks") String requestTasks, @RequestPart(value = "files", required = false) List<MultipartFile> files) throws JsonProcessingException {
        log.info("In Save Task controller");
        GetTasksResponse getTasksResponse = new GetTasksResponse();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        RequestCAPTask requestCAPTask = objectMapper.readValue(requestTasks, RequestCAPTask.class);
        try {
            getTasksResponse = incidentService.saveTasksForCap(requestCAPTask, files);
            return new ResponseEntity<>(getTasksResponse, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception occurred in saveTasksForCap {} ", e.getMessage());
            return new ResponseEntity<>(getTasksResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/saveIncidentInterim")
    public ResponseEntity<SaveInterimResponse> saveIncidentInterim(@RequestPart("interim") String requestInterim, @RequestPart(value = "files", required = false) List<MultipartFile> files) throws JsonProcessingException {
        log.info("In saveIncidentInterim controller");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        RequestIncidentInterim requestIncidentInterim = objectMapper.readValue(requestInterim, RequestIncidentInterim.class);
        SaveInterimResponse saveInterimResponse = new SaveInterimResponse();
        try {
            saveInterimResponse = incidentService.saveIncidentInterim(requestIncidentInterim, files);
            return new ResponseEntity<>(saveInterimResponse, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception occurred in saveIncidentInterim controller {}", e.getMessage());
            return new ResponseEntity<>(saveInterimResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/saveIncidentAssign")
    public ResponseEntity<IncidentAssignResponse> saveIncidentAssign(@RequestBody RequestIncidentAssign requestIncidentAssign) {
        try {
            IncidentAssignResponse incidentAssignResponse = incidentService.assignIncident(requestIncidentAssign);
            return new ResponseEntity<>(incidentAssignResponse, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/uploadDocuments")
    public ResponseEntity<List<DocumentEntity>> uploadDocuments(@RequestParam("files") List<MultipartFile> files, @RequestParam("incidentId") int incidentId, @RequestParam("entityId") int entityId, @RequestParam("source") String source, @RequestParam("userId") int userId) {
        try {
            List<DocumentEntity> storageEntities = storageService.uploadFiles(files, incidentId, entityId, source, userId);
            return new ResponseEntity<>(storageEntities, HttpStatus.OK);

        } catch (Exception e) {
            log.error("Exception occurred in uploadDocuments controller {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/savePreventiveAction")
    public ResponseEntity<PreventiveActionResponse> savePreventiveAction(@RequestPart("preventive") String preventive, @RequestPart(value = "files", required = false) List<MultipartFile> files) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        PreventiveActionDTO preventiveActionDTO = objectMapper.readValue(preventive, PreventiveActionDTO.class);
        log.info("In savePreventiveAction controller");
        PreventiveActionResponse preventiveActionResponse = new PreventiveActionResponse();
        try {
            preventiveActionResponse = incidentService.savePreventiveActionResponse(preventiveActionDTO, files);
            return new ResponseEntity<>(preventiveActionResponse, preventiveActionResponse.getPreventiveActionDetails() != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Exception occurred in savePreventiveAction {} ", e.getMessage());
            return new ResponseEntity<>(preventiveActionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/saveRootCause")
    public ResponseEntity<RootCauseResponse> saveRootCause(@RequestPart("rootCause") String rootCause, @RequestPart(value = "files", required = false) List<MultipartFile> files) throws JsonProcessingException {
        log.info("In saveRootCause controller");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        RootCauseAnalysis rootCauseAnalysis = objectMapper.readValue(rootCause, RootCauseAnalysis.class);
        try {
            RootCauseResponse rootCauseResponse = incidentService.saveRootCauseAction(rootCauseAnalysis, files);
            return new ResponseEntity<>(rootCauseResponse, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception occurred in saveRootCause controller {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/saveIncidentChats")
    public ResponseEntity<IncidentChatResponse> saveIncidentChats(@RequestBody RequestChatDto requestChatDto) {
        log.info("In saveIncidentChats controller");
        try {
            IncidentChatResponse incidentChatResponse = incidentService.saveIncidentChat(requestChatDto);
            return new ResponseEntity<>(incidentChatResponse, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception occurred in saveIncidentChats controller {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/getIncidentChats")
    public ResponseEntity<IncidentChatResponse> getIncidentChats(@RequestBody RequestChatDto requestChatDto) {
        log.info("In getIncidentChats controller");
        try {
            IncidentChatResponse incidentChatResponse = incidentService.getChatByIncidentId(requestChatDto);
            return new ResponseEntity<>(incidentChatResponse, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception occurred in getIncidentChats controller {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/downloadFile")
    public ResponseEntity<Resource> downloadFile(@RequestBody RequestDocument requestDocument) {
        return storageService.downloadFileFromS3(requestDocument.getDocumentName());
    }

    @PostMapping("/deleteFile")
    public ResponseEntity<StatusResponse> deleteFile(@RequestBody RequestDocument requestDocument) {
        StatusResponse statusResponse = new StatusResponse();
        try {
            statusResponse = storageService.updateIsActive(requestDocument.getDocumentId(), requestDocument.getDocumentName());
            return new ResponseEntity<>(statusResponse, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Failed to Delete {}", e.getMessage());
            return new ResponseEntity<>(statusResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/getAllFields")
    public ResponseEntity<FieldsResponse> getAllFields() {
        FieldsResponse fieldsResponse = new FieldsResponse();
        try {
            fieldsResponse = incidentService.getAllFields();
            return new ResponseEntity<>(fieldsResponse, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception occurred in getAllFields Controller {} ", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/createFields")
    public ResponseEntity<String> createField(@RequestBody Map<String, Object> fieldData) {
        try {
            Fields newField = new Fields();
            ObjectMapper objectMapper = new ObjectMapper();
            String fieldJson = objectMapper.writeValueAsString(fieldData);
            newField.setFieldData(fieldJson);
            incidentService.saveField(newField);
            return ResponseEntity.ok("Field saved successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error saving field: " + e.getMessage());
        }
    }

    @PostMapping("/getIncidentFields")
    public ResponseEntity<List<IncidentFieldsDTO>> getFieldsByIncidentId(@RequestBody RequestChatDto requestChatDto) {
        List<IncidentFieldsDTO> fields = incidentService.getFieldsByIncidentId(requestChatDto.getIncidentId());
        return ResponseEntity.ok(fields);
    }

    @PostMapping("/deleteField")
    public ResponseEntity<StatusResponse> deleteField(@RequestBody RequestFields requestFields) {
        try {
            StatusResponse statusResponse = incidentService.deleteField(requestFields.getFieldId());
            return new ResponseEntity<>(statusResponse, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception occurred in deleteField {} ", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/deleteIncidentField")
    public ResponseEntity<StatusResponse> deleteIncidentField(@RequestBody RequestFields requestFields) {
        try {
            StatusResponse statusResponse = incidentService.deleteIncidentField(requestFields.getIncidentId(), requestFields.getFieldId());
            return new ResponseEntity<>(statusResponse, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception occurred in deleteField {} ", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/submitFields")
    public ResponseEntity<String> submitFields(@RequestBody FieldSubmissionRequest request) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            for (Map<String, Object> fieldData : request.getFields()) {
                IncidentField incidentField = new IncidentField();

                // Parse incidentId and fieldId safely
                try {
                    incidentField.setIsActive(Integer.parseInt(fieldData.get("is_active").toString()));
                    incidentField.setIncidentId(Integer.parseInt(fieldData.get("incident_id").toString()));
                    incidentField.setFieldId(Integer.parseInt(fieldData.get("field_id").toString()));
                } catch (NumberFormatException e) {
                    log.error("Invalid number format for incident_id or field_id: {}", e.getMessage());
                    return ResponseEntity.status(400).body("Invalid format for incident_id or field_id.");
                }

                // Handle selected_option, ensuring it is valid JSON
                if (fieldData.get("selected_option") != null) {
                    try {
                        String selectedOptionJson = objectMapper.writeValueAsString(fieldData.get("selected_option"));
                        incidentField.setSelectedOption(selectedOptionJson);
                    } catch (Exception e) {
                        log.error("Error serializing selected_option: {}", e.getMessage());
                        return ResponseEntity.status(400).body("Invalid JSON format for selected_option.");
                    }
                }

                incidentFieldRepo.save(incidentField);
            }
            return ResponseEntity.ok("Fields saved successfully.");
        } catch (Exception e) {
            log.error("Exception in submit: {}", e.getMessage());
            return ResponseEntity.status(500).body("Error saving fields.");
        }
    }

    //@PostMapping("/search")
    public String search(@RequestBody IncidentsDTO incidentsDTO) throws Exception {
        String prompt = incidentsDTO.getUserPrompt();
        float[] promptEmbedding = openAIUtil.getEmbeddings(List.of(prompt)).get(0);
        List<String> textSections = pdfUtil.extractTextByPages("C:\\Users\\ADMIN\\Desktop\\iAssure services\\iassure\\Iassure\\src\\main\\resources\\Moderate_SOP_Document.pdf");
        List<float[]> embeddings = openAIUtil.getEmbeddings(textSections);
        float maxSimilarity = -1;
        String bestMatch = "";

        for (int i = 0; i < embeddings.size(); i++) {
            float similarity = pdfUtil.cosineSimilarity(promptEmbedding, embeddings.get(i));
            if (similarity > maxSimilarity) {
                maxSimilarity = similarity;
                bestMatch = textSections.get(i);
            }
        }

        return pdfUtil.extractSectionByPrompt(bestMatch, prompt);

    }

    @PostMapping("/uploadQdrant")
    public String uploadFile(@RequestParam("file") List<MultipartFile> files) {
        try {
            fileUploadService.uploadAndProcessFiles(files);
            return "File uploaded and processed successfully";
        } catch (IOException e) {
            return "File upload failed: " + e.getMessage();
        }
    }

    @GetMapping("/search")
    public String search(@RequestParam String query) {
        return searchService.search(query);
    }

    @PostMapping("/getReport")
    public ResponseEntity<InputStreamResource> getReportByIncidentId(@RequestBody IncidentsDTO incidentsDTO) {
        return incidentService.getReport(incidentsDTO.getIncidentId(), incidentsDTO.getOrgId(), incidentsDTO.getUserId());

    }

    @PostMapping("/splitFile")
    public void splitFile(@RequestBody SplitPDFRequest splitPDFRequest){
        fileSplit.splitPDF(splitPDFRequest);
    }
}