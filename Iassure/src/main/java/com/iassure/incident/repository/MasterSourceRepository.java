package com.iassure.incident.repository;

import com.iassure.incident.entity.MasterSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MasterSourceRepository extends JpaRepository<MasterSource,Integer> {

    @Query("SELECT ms FROM MasterSource ms WHERE ms.sourceName = :sourceName AND ms.isActive = 1")
    List<MasterSource> findBySourceNameAndIsActive(String sourceName);

    @Query("SELECT ms FROM MasterSource ms WHERE ms.sourceType = :sourceType AND ms.isActive = 1")
    MasterSource findBySourceTypeAndIsActive(String sourceType);


    boolean existsBySourceTypeAndIsActive(String sourceType, int i);
}