package com.iassure.incident.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import com.iassure.constants.AppConstants;
import com.iassure.incident.entity.DocumentEntity;
import com.iassure.incident.repository.StorageRepository;
import com.iassure.incident.response.StatusResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StorageServiceImpl implements StorageService {

    @Value("${application.bucket.name}")
    String bucketName;

    @Autowired
    AmazonS3 s3Client;

    @Autowired
    StorageRepository storageRepository;

    @Override
    public List<DocumentEntity> uploadFiles(List<MultipartFile> files, int entityId1, int entityId2, String documentType,int userId) {
        List<CompletableFuture<DocumentEntity>> futures = files.stream()
                .map(file -> CompletableFuture.supplyAsync(() -> uploadFile(file,entityId1, entityId2, documentType,userId), Executors.newFixedThreadPool(10)))
                .toList();

        List<DocumentEntity> storageEntities = futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());

        return storageRepository.saveAll(storageEntities);
    }

    private DocumentEntity uploadFile(MultipartFile file, int entityId1, int entityId2, String documentType,int userId) {
        String fileName = file.getOriginalFilename();

        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());

            s3Client.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), metadata));

            String fileUrl = s3Client.getUrl(bucketName, fileName).toString();
            DocumentEntity storageEntity = new DocumentEntity();
            storageEntity.setEntityId1(entityId1);
            storageEntity.setEntityId2(entityId2);
            storageEntity.setDocumentName(fileName);
            storageEntity.setDocumentType(documentType);
            storageEntity.setDocumentSize((int) file.getSize());
            storageEntity.setDocumentDetails(file.getContentType());
            storageEntity.setUploadDate(new Timestamp(System.currentTimeMillis()));
            storageEntity.setDocumentUrl(fileUrl);
            storageEntity.setIsActive(1);
            storageEntity.setUserId(userId);

            return storageEntity;
        } catch (IOException e) {
            // Log the exception with a proper logging framework
            throw new RuntimeException("Error uploading file to S3: " + fileName, e);
        }
    }

    @Override
    public List<DocumentEntity> getAllFileDetails(int incidentId,String type) {

        // Retrieve all StorageEntity records from the database
        List<DocumentEntity> storageEntities = storageRepository.findByEntityId1AndDocumentType(incidentId,type);

        return storageEntities.stream().peek(entity -> {

            // Generate a presigned URL for each document in S3
            URL url = s3Client.generatePresignedUrl(new GeneratePresignedUrlRequest(bucketName, entity.getDocumentName())
                    .withMethod(HttpMethod.GET)
                    .withExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))); // 1 hour expiration
            // Set the generated presigned URL to the documentURL field of the entity
            entity.setDocumentUrl(url.toString());
        }).collect(Collectors.toList());
    }

    @Override
    public List<DocumentEntity> getAllFileDetailsForTask(int incidentId,int entityId2,String type) {

        // Retrieve all StorageEntity records from the database
        List<DocumentEntity> storageEntities = storageRepository.findFilesForTasks(incidentId,entityId2,type);

        return storageEntities.stream().peek(entity -> {

            // Generate a presigned URL for each document in S3
            URL url = s3Client.generatePresignedUrl(new GeneratePresignedUrlRequest(bucketName, entity.getDocumentName())
                    .withMethod(HttpMethod.GET)
                    .withExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))); // 1 hour expiration
            // Set the generated presigned URL to the documentURL field of the entity
            entity.setDocumentUrl(url.toString());
        }).collect(Collectors.toList());
    }



    @Override
    public StatusResponse updateIsActive(int documentId, String documentName) {
        StatusResponse statusResponse = new StatusResponse();
        // Retrieve the document from the repository based on documentId, documentName, and documentType
        Optional<DocumentEntity> optionalEntity = storageRepository.findByDocumentId(documentId);

        if (optionalEntity.isPresent()) {
            DocumentEntity entity = optionalEntity.get();
            entity.setIsActive(0);

            // Save the updated entity back to the repository
            storageRepository.save(entity);
            statusResponse.setResponseCode(200);
            statusResponse.setResponseMessage("File Deleted Successfully");
        }
        else {
            statusResponse.setResponseMessage(AppConstants.INTERNAL_SERVER_ERROR);
            statusResponse.setResponseCode(500);

        }
        return statusResponse;
    }

    @Override
    public ResponseEntity<Resource> downloadFileFromS3(String fileName) {
        // Retrieve the S3 object using the provided bucket name and file name.
        S3Object s3Object = s3Client.getObject(bucketName, fileName);

        // Get the input stream of the S3 object content.
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            byte[] content = IOUtils.toByteArray(inputStream);
            ByteArrayResource resource = new ByteArrayResource(content);

            // Build and return the response entity with the file content and appropriate headers.
            return ResponseEntity.ok()
                    .contentLength(content.length)
                    .header("Content-Type", s3Object.getObjectMetadata().getContentType())
                    .header("Content-Disposition", "attachment; filename=\"" + fileName + "\"")
                    .body(resource);// Set the body of the response to the ByteArrayResource.
        } catch (IOException e) {
            throw new RuntimeException("Error downloading file from S3", e);
        }
    }
}
