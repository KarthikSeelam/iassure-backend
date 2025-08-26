package com.iassure.incident.repository;

import com.iassure.incident.entity.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StorageRepository extends JpaRepository<DocumentEntity, Integer>, JpaSpecificationExecutor<DocumentEntity> {
    Optional<DocumentEntity> findByDocumentId(Integer documentId);
    @Query(value = "SELECT * FROM documentrepository WHERE EntityID1 = :entityId1 AND DocumentType = :type AND IsActive = 1",nativeQuery = true)
    List<DocumentEntity> findByEntityId1AndDocumentType(int entityId1,String type);
    @Query(value = "SELECT * FROM documentrepository WHERE EntityID1 = :entityId1 AND EntityID2 = :entityId2 AND DocumentType = :type AND IsActive = 1",nativeQuery = true)
    List<DocumentEntity> findFilesForTasks(int entityId1,int entityId2,String type);
}
