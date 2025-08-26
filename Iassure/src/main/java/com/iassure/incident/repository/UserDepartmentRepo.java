package com.iassure.incident.repository;

import com.iassure.incident.entity.UsersByDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDepartmentRepo extends JpaRepository<UsersByDepartment,Integer> {
    @Procedure(procedureName = "USP_GetUsersByDepartment")
    List<UsersByDepartment> getUserDetailsByDepartment(int orgId, int departmentId);
}
