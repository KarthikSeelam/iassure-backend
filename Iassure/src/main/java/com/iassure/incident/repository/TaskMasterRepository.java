package com.iassure.incident.repository;

import com.iassure.incident.entity.TaskMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskMasterRepository extends JpaRepository<TaskMaster,Integer> {
    boolean existsByTaskName(String taskName);

    @Query(value = "SELECT * from TaskMaster where TaskName = :taskName",nativeQuery = true)
    TaskMaster findByTaskName(String taskName);

    @Query(value = "SELECT * from TaskMaster where DepartmentID = :departmentId",nativeQuery = true)
    List<TaskMaster> getTaskList(int departmentId);
}
