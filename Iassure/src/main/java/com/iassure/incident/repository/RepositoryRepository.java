package com.iassure.incident.repository;

import com.iassure.incident.entity.RepositoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositoryRepository extends JpaRepository<RepositoryEntity,Integer> {

    List<RepositoryEntity> findByDirectoryName(String directoryName);
}