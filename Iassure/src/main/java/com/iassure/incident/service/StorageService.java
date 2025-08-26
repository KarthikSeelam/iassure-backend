package com.iassure.incident.service;




import com.iassure.incident.entity.DocumentEntity;
import com.iassure.incident.response.StatusResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface StorageService {

    List<DocumentEntity> uploadFiles(List<MultipartFile> files, int entityId1, int entityId2, String documentType,int userId);

    List<DocumentEntity> getAllFileDetails(int incidentId,String type);

    List<DocumentEntity> getAllFileDetailsForTask(int incidentId, int entityId2, String type);

    public StatusResponse updateIsActive(int documentId, String documentName);
    public ResponseEntity<Resource> downloadFileFromS3(String fileName);
}
