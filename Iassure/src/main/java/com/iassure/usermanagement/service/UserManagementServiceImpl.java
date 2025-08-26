package com.iassure.usermanagement.service;

import com.iassure.constants.AppConstants;
import com.iassure.incident.dto.RequestBodyDTO;
import com.iassure.incident.entity.Department;
import com.iassure.incident.entity.Users;
import com.iassure.incident.entity.UsersByDepartment;
import com.iassure.incident.repository.DepartmentRepo;
import com.iassure.incident.repository.FetchUsersRepo;
import com.iassure.incident.repository.GetEmployeeAndManagerRepo;
import com.iassure.incident.repository.UserDepartmentRepo;
import com.iassure.incident.response.StatusResponse;
import com.iassure.usermanagement.dto.RequestCreateUserDTO;
import com.iassure.usermanagement.dto.RequestLogin;
import com.iassure.usermanagement.entity.*;
import com.iassure.usermanagement.repository.*;
import com.iassure.usermanagement.response.CreateUserResponse;
import com.iassure.usermanagement.response.GetAllUsersResponse;
import com.iassure.usermanagement.response.GetUserByIdResponse;
import com.iassure.usermanagement.response.LoginResponse;
import com.iassure.util.DateUtil;
import com.iassure.util.EmailUtil;
import com.iassure.util.GeneratePasswordUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.StringWriter;
import java.util.*;

@Service
@Log4j2
@Transactional
public class UserManagementServiceImpl implements UserManagementService {
    @Autowired
    UserTypesRepository userTypesRepository;
    @Autowired
    UserLoginRepo userLoginRepo;
    @Autowired
    DepartmentRepo departmentRepo;
    @Autowired
    UserDepartmentRepo userDepartmentRepo;
    @Autowired
    FetchUsersRepo fetchUsersRepo;
    @Autowired
    GetEmployeeAndManagerRepo getEmployeeAndManagerRepo;
    @Autowired
    GeneratePasswordUtil generatePasswordUtil;
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    DepartmentUserRepository departmentUserRepository;
    @Autowired
    UserDetailsByIdRepo userDetailsByIdRepo;
    @Autowired
    DateUtil dateUtil;
    @Autowired
    EmailUtil emailUtil;

    @Override
    public List<UserTypes> getAllUserTypes(){
        List<UserTypes> userTypes = new ArrayList<>();
        try{
            userTypes = userTypesRepository.findAll();
            if(!userTypes.isEmpty()){
                log.info("User types Successfully fetched");
            }else {
                log.info("User Types Not Found");
            }

        }catch (Exception e){
            log.error("Exception occurred in getAllUserTypes service {} ",e.getMessage());
        }
        return userTypes;
    }
    @Override
    public Department saveDepartments(String deptName) {
        Department department = new Department();
        department.setDepartmentName(deptName);
        try {
            department = departmentRepo.save(department);
            log.info("Success");
        } catch (Exception e) {
            log.error("Exception");
        }
        return department;
    }

    @Override
    public List<Department> getAllDepartments() {
        List<Department> departments = new ArrayList<>();
        try {
            departments = departmentRepo.findAll();
        } catch (Exception e) {
            log.error("Exception occurred in getAllDepartment {} ", e.getMessage());
        }
        return departments;
    }

    @Override
    public List<UsersByDepartment> getUsersByDepartment(int orgId, int departmentId) {
        List<UsersByDepartment> usersByDepartments = new ArrayList<>();
        try {
            usersByDepartments = userDepartmentRepo.getUserDetailsByDepartment(orgId, departmentId);
        } catch (Exception e) {
            log.error("Exception occurred in getUsersByDepartment {} ", e.getMessage());

        }
        return usersByDepartments;
    }

    @Override
    public GetAllUsersResponse getAllUsers(Integer orgId, String flag, Integer departmentId) {
        StatusResponse statusResponse = new StatusResponse();
        List<Users> users = new ArrayList<>();
        int count =0 ;
        try {
            users = fetchUsersRepo.getAllUsers(orgId, flag, departmentId);
            count = users.size();
            if(!users.isEmpty()){
                statusResponse.setResponseCode(200);
                statusResponse.setResponseMessage("Users Fetch Successful");
            }
            else {
                statusResponse.setResponseCode(400);
                statusResponse.setResponseMessage("No Users");
            }
        } catch (Exception e) {
            statusResponse.setResponseMessage(AppConstants.INTERNAL_SERVER_ERROR);
            statusResponse.setResponseCode(500);
            log.error("Exception occurred in getAllUsers {}", e.getMessage());
        }
        return new GetAllUsersResponse(statusResponse,count,users); // Always return the users list, even if it is empty
    }

    @Override
    public List<Users> getUsersAndManagers(RequestBodyDTO requestBodyDTO) {
        List<Users> users = new ArrayList<>();
        try {
            users = getEmployeeAndManagerRepo.getListOfUsers(requestBodyDTO.getOrgId(), requestBodyDTO.getFlag(), requestBodyDTO.getDepartmentId());
        } catch (Exception e) {
            log.error("Exception occurred in getUsersAndManagers {}", e.getMessage());
        }
        return users;
    }
    @Override
    public GetUserByIdResponse getUserDetailsById(int userId) {
        StatusResponse statusResponse = new StatusResponse();
        UserDetailsById users = new UserDetailsById();
        try {
            users = userDetailsByIdRepo.getUserDetailsById(userId);
            if (users != null) {
                statusResponse.setResponseCode(200);
                statusResponse.setResponseMessage("User Details Fetched successfully");
            } else {
                statusResponse.setResponseMessage("No records");
                statusResponse.setResponseCode(400);
            }
        } catch (Exception e) {
            statusResponse.setResponseCode(500);
            statusResponse.setResponseMessage(AppConstants.INTERNAL_SERVER_ERROR);
        }
        return new GetUserByIdResponse(statusResponse, users);

    }
    @Override
    public StatusResponse deleteUser(RequestCreateUserDTO requestCreateUserDTO){
        StatusResponse statusResponse = new StatusResponse();
        try{
            usersRepository.deleteUser(requestCreateUserDTO.getUserId());
            statusResponse.setResponseMessage("User Removed Successfully");
            statusResponse.setResponseCode(200);
        }catch (Exception e){
            statusResponse.setResponseCode(500);
            statusResponse.setResponseMessage(AppConstants.INTERNAL_SERVER_ERROR);
        }
        return statusResponse;
    }

    @Override
    public CreateUserResponse createUser(RequestCreateUserDTO requestCreateUserDTO) {
        StatusResponse statusResponse = new StatusResponse();
        UsersEntity users = new UsersEntity();
        DepartmentUserMapping departmentUserMapping = new DepartmentUserMapping();

        try {
            // Check if email already exists for creating a user
            if ("I".equalsIgnoreCase(requestCreateUserDTO.getFlag())) {
                Optional<UsersEntity> existingUser = usersRepository.findByUsername(requestCreateUserDTO.getUsername());
                if (existingUser.isPresent()) {
                    // Email already exists
                    statusResponse.setResponseMessage("Email already exists");
                    statusResponse.setResponseCode(409); // 409 Conflict
                    return new CreateUserResponse(statusResponse, null);
                }

                // Proceed with user creation
                BeanUtils.copyProperties(requestCreateUserDTO, users);
                String tempPassword = generatePasswordUtil.generate();
                users.setPassword(tempPassword); // Set generated password
                users.setIsActive(1); // New user is active
                users.setLoginRequired(1); // Login required
                users.setLoginFirstTime(1);
                users.setCreatedAt(dateUtil.getTimestamp().toString());// First-time login

                // Save the new user
                users = usersRepository.save(users);

                // Map user to department
                departmentUserMapping.setUserId(users.getUserId());
                departmentUserMapping.setDepartmentId(users.getDepartmentId());
                departmentUserMapping.setCreatedAt(dateUtil.getTimestamp().toString());
                departmentUserRepository.save(departmentUserMapping);
                List<String> emails = new ArrayList<>();
                emails.add(users.getUsername());
                boolean result = sendMail(users.getFirstName(), users.getPassword(),emails);
                statusResponse.setResponseMessage("User Created Successfully");
                statusResponse.setResponseCode(200);

            } else if ("U".equalsIgnoreCase(requestCreateUserDTO.getFlag())) {
                // Handle user update logic
                Optional<UsersEntity> existingUser = usersRepository.findById(requestCreateUserDTO.getUserId());
                if (!existingUser.isPresent()) {
                    // User not found
                    statusResponse.setResponseMessage("User Not Found");
                    statusResponse.setResponseCode(404); // 404 Not Found
                    return new CreateUserResponse(statusResponse, null);
                }

                // Update existing user details
                users = existingUser.get();
                BeanUtils.copyProperties(requestCreateUserDTO, users, "password", "userId");
                users.setUpdatedAt(dateUtil.getTimestamp().toString());// Don't update password or userId
                usersRepository.save(users);

                // Fetch existing DepartmentUserMapping based on userId or departmentId
                Optional<DepartmentUserMapping> existingMapping = departmentUserRepository.findByUserId(users.getUserId());

                if (existingMapping.isPresent()) {
                    // Retrieve the existing mapping
                    departmentUserMapping = existingMapping.get();

                    // Check if the departmentId has changed
                    if (!Objects.equals(departmentUserMapping.getDepartmentId(), users.getDepartmentId())) {
                        // Update the departmentId if it's different
                        departmentUserMapping.setDepartmentId(users.getDepartmentId());
                        departmentUserMapping.setCreatedAt(dateUtil.getTimestamp().toString()); // Assuming there's an updatedAt field
                    }
                } else {
                    // Create a new mapping if it doesn't exist
                    departmentUserMapping = new DepartmentUserMapping();
                    departmentUserMapping.setUserId(users.getUserId());
                    departmentUserMapping.setDepartmentId(users.getDepartmentId());
                    departmentUserMapping.setCreatedAt(dateUtil.getTimestamp().toString());
                }

                // Save the updated department user mapping
                departmentUserRepository.save(departmentUserMapping);

                statusResponse.setResponseMessage("User Updated Successfully");
                statusResponse.setResponseCode(200);
            }

        } catch (Exception e) {
            log.error("Error occurred in createUser method: {}", e.getMessage());
            statusResponse.setResponseCode(500);
            statusResponse.setResponseMessage(AppConstants.INTERNAL_SERVER_ERROR);
        }

        return new CreateUserResponse(statusResponse, users);
    }

    private boolean sendMail(String firstName,String passsword,List<String> email){
        String templatePath = "/templates/email-template.ftl"; // Relative path to the template from the classpath
        boolean result = false;
        try {
            // Create FreeMarker Configuration
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_30);
            cfg.setClassForTemplateLoading(this.getClass(), "/"); // Set the classpath for template loading

            // Load the template
            Template template = cfg.getTemplate(templatePath);

            // Create the data model for template processing
            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("firstname", firstName);
            dataModel.put("password", passsword);
            dataModel.put("email", email);


            // Process the template with the data model
            StringWriter writer = new StringWriter();
            template.process(dataModel, writer);

            // Get the processed content as a String
            String content = writer.toString();

            // Send the email
            emailUtil.sendEmail(content, email, "User Creation");

            result = true;
        } catch (Exception ex) {
            log.error("Error in addNewUser in UserServiceImpl: {}", ex.getMessage());
        }
        return result;
    }



    @Override
    public LoginResponse userLogin(RequestLogin requestLogin) {
        StatusResponse statusResponse = new StatusResponse();
        UserLogin userLogin = null;  // Set to null initially to avoid issues
        try {
            // Fetch user login details based on username and password
            userLogin = userLoginRepo.getLoginDetails(requestLogin.getUsername(), requestLogin.getPassword());

            // Check if userLogin is null, indicating invalid credentials
            if (userLogin == null) {
                statusResponse.setResponseCode(400);
                statusResponse.setResponseMessage("Invalid Credentials");
                return new LoginResponse(statusResponse, null);
            }

            // Handle case where user is logging in for the first time
            if (userLogin.getLoginFirstTime() == 1) {
                statusResponse.setResponseCode(203);
                statusResponse.setResponseMessage("Login First Time, Please change password");
                return new LoginResponse(statusResponse,null);
            } else {
                // Successful login
                statusResponse.setResponseMessage("Login Successful");
                statusResponse.setResponseCode(200);
            }

        } catch (Exception e) {
            // Handle unexpected exceptions
            statusResponse.setResponseMessage(AppConstants.INTERNAL_SERVER_ERROR);
            statusResponse.setResponseCode(500);
            log.error("Exception occurred during user login: {}", e.getMessage());
        }

        return new LoginResponse(statusResponse, userLogin);
    }

    @Override
    public StatusResponse forgotPassword(RequestLogin requestLogin){
        StatusResponse statusResponse = new StatusResponse();
        try{
            userLoginRepo.forgotPassword(requestLogin.getPassword(),requestLogin.getUsername());
            statusResponse.setResponseCode(200);
            statusResponse.setResponseMessage("Password successfully changed");
        }catch (Exception e){
            statusResponse.setResponseMessage(AppConstants.INTERNAL_SERVER_ERROR);
            statusResponse.setResponseCode(500);
        }
        return statusResponse;
    }


}
