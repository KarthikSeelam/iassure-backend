package com.iassure.user.service;


import com.iassure.dto.UserDetailsDTO;
import com.iassure.entity.*;
import com.iassure.repositary.*;
import com.iassure.user.dto.*;
import com.iassure.util.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *
 * @author Naveen Kumar Chintala
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDetailsRepository userDetailsRepository;

    @Autowired
    DesignationDetailsRepository designationDetailsRepository;

    @Autowired
    UserTitleTypeMasterRepository userTitleTypeMasterRepository;

    @Autowired
    OrganizationDetailsRepository organizationDetailsRepository;

    @Autowired
    DateUtil dateUtil;

    @Autowired
    UserWorkProfileMappingRepository userWorkProfileMappingRepository;


    @Override
    public UserDetails findById(Integer id) {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(1L, "USER"));
        Optional<UserDetails> result = userDetailsRepository.findById(id);
        return result.orElse(null);
    }

    @Override
    public UserDetails findByEmail(String email) {

        //Setting Roles
        return userDetailsRepository.findByUsername(email);
    }


    @Override
    public UserDetails findByMobileNoOrEmail(String mobileNoOrEmail) {

        //List<Role> roles = roleRepository.findByIdIn(user.getId());

        // user.setRoles(new HashSet<>(roles));
        return userDetailsRepository.findByMobileNoOrEmail(mobileNoOrEmail);
    }

    @Override
    public List<UserDetails> findByIdIn(List<Integer> userIds) {
        //List<Role> roles = new ArrayList<>();
        /*for (User userData : userDetails) {
            List<Role> userRoleDetails = roleRepository.findByIdIn(userData.getId());

            Set<Role> roles = new HashSet <Role>(userRoleDetails);
            userData.setRoles(roles);
        }*/
        return userDetailsRepository.findByuserIdIn(userIds);
    }

   /* @Override
    public UserProfileDTO findUserProfileByMobileNoOrEmail(String mobileNoOrEmail) {
        return userRepository.findUserProfileByMobileNoOrEmail(mobileNoOrEmail);
    }

    @Override
    public Boolean existsByMobileNo(String mobileNo) {
        return userRepository.existsByMobileNo(mobileNo);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public List<Integer> retrieveTeamUserIdsById(String encryptedUserId) {
        return userRepository.findTeamUserIdsById(encryptedUserId);
    }*/

    @Override
    public boolean findByContactNo(String mobile) {
        UserDetails user = userDetailsRepository.findByContactNo(mobile);
        return user != null;
    }

    @Override
    public List<UserDetails> getUsersList() {
        List<UserDetails> list = userDetailsRepository.findAll();
        return list;
    }

    @Override
    public boolean addUser(UserDetailsDTO user) {
        boolean result = false;
        try {
            UserDetails newUser = new UserDetails();
            BeanUtils.copyProperties(user, newUser);
            newUser = userDetailsRepository.save(newUser);
            if(newUser.getUserId() > 0)
                result = true;
        } catch (Exception e) {
            result = false;
            System.out.println("Error in addNewUser" + e.getMessage());
        }
        return result;
    }

    @Override
    public boolean updateUserWorkProfile(UserWorkProfileDTO workProfileDTO){
        boolean result = false;
        try{
            if(workProfileDTO.getCustomerList() != null && !workProfileDTO.getCustomerList().isEmpty()){
                for (Integer orgId : workProfileDTO.getCustomerList()){
                    UserWorkProfileMapping workProfile = new UserWorkProfileMapping();
                    workProfile.setFkUserDetailsId(workProfileDTO.getUserDetailsId());
                    workProfile.setFkStatusId(workProfileDTO.getStatusId());
                    OrganizationDetails org = new OrganizationDetails();
                    org.setOrganizationId(orgId);
                    org = organizationDetailsRepository.save(org);
                    workProfile.setOrganizationDetails(org);
                    workProfile.setCreatedOn(dateUtil.getTimestamp());
                    workProfile.setUpdatedOn(dateUtil.getTimestamp());
                    workProfile = userWorkProfileMappingRepository.save(workProfile);
                }
            }
            result = true;
        }catch (Exception e) {
            result = false;
            System.out.println("Error in updateUserWorkProfile" + e.getMessage());
        }
        return result;
    }
    @Override
    public boolean updateUser(UserDetailsDTO user){
        boolean result = false;
        try{
            UserDetails newUser = userDetailsRepository.findByUserId(user.getUserId());
            BeanUtils.copyProperties(user,newUser);
            userDetailsRepository.save(newUser);
            result = true;
        }catch (Exception e){
            result = false;
            System.out.println("Error in addNewUser"+e.getMessage());
        }
        return result;
    }

    @Override
    public boolean addDesignation(DesignationDTO designation){
        boolean result = false;
        try{
            DesignationDetails newDsgn = new DesignationDetails();
            BeanUtils.copyProperties(designation,newDsgn);
            newDsgn = designationDetailsRepository.save(newDsgn);
            if(newDsgn.getDesignationId() > 0)
                result = true;
        }catch (Exception e){
            result = false;
            System.out.println("Error in addDesignation"+e.getMessage());
        }
        return result;
    }

    @Override
    public boolean addUserTitleTypeJson(UserTitleTypeMasterDTO userTitle) {
        boolean result = false;
        try{
            UserTitleTypeMaster userTitleTypeMaster =  new UserTitleTypeMaster();
            //userTitleTypeMaster.setCreatedBy((int) request.getSession().getAttribute("loggedUserId"));
            //userTitleTypeMaster.setCreatedOn(dateUtil.getTimestamp());
            BeanUtils.copyProperties(userTitle,userTitleTypeMaster);
            userTitleTypeMasterRepository.save(userTitleTypeMaster);
            result = true;
        }catch(Exception ex)
        {
            ex.printStackTrace();
            System.out.println("Error in addTitleJson "+ex.getMessage());
            result = true;
        }
        return result;
    }

    @Override
    public List<UserTitleTypeMasterDTO> getUserTitleTypeList() {
        List<UserTitleTypeMasterDTO> userTypeList = new ArrayList<>();
        try {
            List<UserTitleTypeMaster> list = userTitleTypeMasterRepository.findAll();
            if(list != null && !list.isEmpty()){
                for(UserTitleTypeMaster title : list){
                    UserTitleTypeMasterDTO typeMasterDTO = new UserTitleTypeMasterDTO();
                    BeanUtils.copyProperties(title,typeMasterDTO);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error in getUserTitleTypeList "+ex.getMessage());
        }
        return userTypeList;
    }

    @Override
    public List<DesignationDTO> getDesignationDetailsList(Integer organizationId) {
        List<DesignationDTO> designationList = new ArrayList<>();
        try {
            List<DesignationDetails> list = designationDetailsRepository.findByOrganizationId(organizationId);
            if(list != null && !list.isEmpty()){
                for(DesignationDetails designation : list){
                    DesignationDTO dto = new DesignationDTO();
                    BeanUtils.copyProperties(designation,dto);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error in getDesignationDetailsList "+ex.getMessage());
        }
        return designationList;
    }

    @Override
    public String validateUserEmailEmployeeId(String email,Integer employeeId){
        String result = "";
        try{
            if (email != null && !email.isEmpty()) {
                if (validateUserEmail(email)) {
                    result += "Email already Exists.";
                }
            }
            if (employeeId != null && employeeId > 0) {
                if (validateUserEmployeeId(employeeId)) {
                    result += "Employee Id already Exists";
                }
            }
        }catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error in validateUserEmailEmployeeId "+ex.getMessage());
        }
        System.out.println("Result "+result);
        return result;
    }

    @Override
    public boolean validateUserEmail(String email){
        boolean result = false;
        try{
            int count = userDetailsRepository.getCountByEmail(email);
            if(count > 0)
                result = true;
            System.out.println("Email count is "+count+" Email is "+email);
        }catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error in validateUserEmail "+ex.getMessage());
        }
        System.out.println("Email result "+result);
        return result;
    }

    @Override
    public boolean validateUserEmployeeId(Integer employeeId){
        boolean result = false;
        try{
            int count = userDetailsRepository.getCountByEmployeeId(employeeId);
            if(count > 0)
                result = true;
            System.out.println("Employee count is "+count+" Employee is "+employeeId);
        }catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error in validateUserEmployeeId "+ex.getMessage());
        }
        System.out.println("Employee result "+result);
        return result;
    }
    @Override
    public List<UserWorkProfileDTO> getUserWorkProfile(Integer userId){
        List<UserWorkProfileDTO> result = new ArrayList<>();
        try{
            List<UserWorkProfileMapping> list = userWorkProfileMappingRepository.findByFkUserDetailsId(userId);
            BeanUtils.copyProperties(list,result);
        }catch (Exception ex) {
            System.out.println("Error in getUserWorkProfile "+ex.getMessage());
        }
        return result;
    }
}