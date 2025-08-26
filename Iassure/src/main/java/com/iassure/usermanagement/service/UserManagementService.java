package com.iassure.usermanagement.service;

import com.iassure.incident.dto.RequestBodyDTO;
import com.iassure.incident.entity.Department;
import com.iassure.incident.entity.Users;
import com.iassure.incident.entity.UsersByDepartment;
import com.iassure.incident.response.StatusResponse;
import com.iassure.usermanagement.dto.RequestCreateUserDTO;
import com.iassure.usermanagement.dto.RequestLogin;
import com.iassure.usermanagement.entity.UserTypes;
import com.iassure.usermanagement.response.CreateUserResponse;
import com.iassure.usermanagement.response.GetAllUsersResponse;
import com.iassure.usermanagement.response.GetUserByIdResponse;
import com.iassure.usermanagement.response.LoginResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserManagementService {
    List<UserTypes> getAllUserTypes();

    Department saveDepartments(String deptName);

    List<Department> getAllDepartments();

    List<UsersByDepartment> getUsersByDepartment(int orgId, int departmentId);

    GetAllUsersResponse getAllUsers(Integer orgId, String flag, Integer departmentId);

    List<Users> getUsersAndManagers(RequestBodyDTO requestBodyDTO);

    GetUserByIdResponse getUserDetailsById(int userId);

    StatusResponse deleteUser(RequestCreateUserDTO requestCreateUserDTO);

    CreateUserResponse createUser(RequestCreateUserDTO requestCreateUserDTO);

    LoginResponse userLogin(RequestLogin requestLogin);

    StatusResponse forgotPassword(RequestLogin requestLogin);
}
