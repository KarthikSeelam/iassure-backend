package com.iassure.user.service;


import com.iassure.dto.UserDetailsDTO;
import com.iassure.entity.UserDetails;
import com.iassure.user.dto.*;

import java.util.List;

/**
 *
 * @author Naveen Kumar Chintala
 */
public interface UserService {

    UserDetails findById(Integer id);

    UserDetails findByEmail(String email);

    UserDetails findByMobileNoOrEmail(String mobileNoOrEmail);

    List<UserDetails> findByIdIn(List<Integer> userIds);

   /* UserProfileDTO findUserProfileByMobileNoOrEmail(String mobileNoOrEmail);

    Boolean existsByMobileNo(String mobileNo);

    Boolean existsByEmail(String email);

    List<Integer> retrieveTeamUserIdsById(String encryptedUserId);*/

    boolean findByContactNo(String mobile);

    public List<UserDetails> getUsersList();

    public boolean addUser(UserDetailsDTO user);
    public boolean updateUser(UserDetailsDTO user);
    public boolean addDesignation(DesignationDTO designation);
    public boolean addUserTitleTypeJson(UserTitleTypeMasterDTO userTitle);
    public List<UserTitleTypeMasterDTO> getUserTitleTypeList();
    public List<DesignationDTO> getDesignationDetailsList(Integer organizationId);
    public String validateUserEmailEmployeeId(String email,Integer employeeId);
    public boolean validateUserEmail(String email);
    public boolean validateUserEmployeeId(Integer employeeId);
    public boolean updateUserWorkProfile(UserWorkProfileDTO workProfileDTO);
    public List<UserWorkProfileDTO> getUserWorkProfile(Integer userId);
}
