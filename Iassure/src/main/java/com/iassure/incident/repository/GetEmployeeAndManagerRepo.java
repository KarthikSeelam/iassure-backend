package com.iassure.incident.repository;

import com.iassure.incident.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GetEmployeeAndManagerRepo extends JpaRepository<Users,Integer> {

    @Procedure(procedureName = "USP_ListUsers")
    List<Users> getListOfUsers(int orgId, String flag, int departmentId);
}
