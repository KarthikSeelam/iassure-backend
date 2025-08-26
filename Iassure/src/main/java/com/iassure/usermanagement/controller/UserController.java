package com.iassure.usermanagement.controller;

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
import com.iassure.usermanagement.service.UserManagementService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@Log4j2
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserManagementService userManagementService;

    @PostMapping("/getUserTypes")
    public ResponseEntity<List<UserTypes>> getUserTypes(){
        List<UserTypes> userTypes = new ArrayList<>();
        try{
            userTypes = userManagementService.getAllUserTypes();
            return new ResponseEntity<>(userTypes, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(userTypes,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/saveDepartment")
    public ResponseEntity<Department> saveDepartment(@RequestParam String deptName){
        Department department = new Department();
        try{
            department = userManagementService.saveDepartments(deptName);
            return new ResponseEntity<>(department,HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception");
            return new ResponseEntity<>(department,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/getAllDepartments")
    public ResponseEntity<List<Department>> getAllDepartments(){
        log.info("In getAllDepartments controller");
        try{
            List<Department> departments = userManagementService.getAllDepartments();
            return new ResponseEntity<>(departments,HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception occurred in getAllDepartments controller {} ",e.getMessage());
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/getUsersByDepartment")
    public ResponseEntity<List<UsersByDepartment>> getUsersByDepartment(@RequestBody RequestBodyDTO requestBodyDTO){
        log.info("In getUsersByDepartment controller");
        try{
            List<UsersByDepartment> usersByDepartments = userManagementService.getUsersByDepartment(requestBodyDTO.getOrgId(), requestBodyDTO.getDepartmentId());
            return new ResponseEntity<>(usersByDepartments, HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception occurred in getUsersByDepartment controller {} ",e.getMessage());
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
    @PostMapping("/getEmployeesAndManager")
    public ResponseEntity<List<Users>> getEmployeesAndManager(@RequestBody RequestBodyDTO requestBodyDTO){
        log.info("In getEmployeesAndManager controller");
        try{
            List<Users> users = userManagementService.getUsersAndManagers(requestBodyDTO);
            return new ResponseEntity<>(users,HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception occurred in getEmployeesAndManager controller {} ",e.getMessage());

            return new ResponseEntity<>(null,HttpStatus.OK);
        }

    }
    @PostMapping("/getAllUsers")
    public ResponseEntity<GetAllUsersResponse> getAllUsers(@RequestBody RequestBodyDTO requestBodyDTO){
        log.info("In getAllUsers controller");
        try{
            GetAllUsersResponse users = userManagementService.getAllUsers(requestBodyDTO.getOrgId(),requestBodyDTO.getFlag(), requestBodyDTO.getDepartmentId());
            return new ResponseEntity<>(users,HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception occurred in getAllUsers controller {} ",e.getMessage());
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/getUsersById")
    public ResponseEntity<GetUserByIdResponse> getUsersById(@RequestBody RequestCreateUserDTO requestCreateUserDTO){
        log.info("In getUsersById controller");
        try{
            GetUserByIdResponse user = userManagementService.getUserDetailsById(requestCreateUserDTO.getUserId());
            return new ResponseEntity<>(user,HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception occurred in getUsersById controller {} ",e.getMessage());
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/deleteUser")
    public ResponseEntity<StatusResponse> deleteUser(@RequestBody RequestCreateUserDTO requestCreateUserDTO){
        log.info("In delete User controller");
        try{
            StatusResponse statusResponse = userManagementService.deleteUser(requestCreateUserDTO);
            return new ResponseEntity<>(statusResponse,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/createUser")
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody RequestCreateUserDTO requestCreateUserDTO){
        log.info("In createUser controller");
        try{
            CreateUserResponse createUserResponse = userManagementService.createUser(requestCreateUserDTO);
            return new ResponseEntity<>(createUserResponse,HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception occurred in createUser controller {}",e.getMessage());
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody RequestLogin requestLogin){
        log.info("In Login controller");
        try{
            LoginResponse loginResponse = userManagementService.userLogin(requestLogin);
            return new ResponseEntity<>(loginResponse,HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception occurred in login controller {} ",e.getMessage());
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/forgotPassword")
    public ResponseEntity<StatusResponse> forgotPassword(@RequestBody RequestLogin requestLogin){
        log.info("In forgot controller");
        try{
            StatusResponse statusResponse = userManagementService.forgotPassword(requestLogin);
            return new ResponseEntity<>(statusResponse,HttpStatus.OK);
        }catch (Exception e){
                return new ResponseEntity<>(null,HttpStatus.OK);
        }

    }
}
