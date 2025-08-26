/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iassure.user.mapper;

import com.iassure.user.dto.DesignationDTO;
import com.iassure.user.dto.OrganizationDTO;
import com.iassure.user.dto.UserProfileDTO;
import com.iassure.user.dto.UserTypeDTO;
import com.iassure.util.EncryptDecrypt;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author rjanumpally
 */
public class UserProfileRowMapper implements RowMapper<UserProfileDTO> {

    EncryptDecrypt edUtil = new EncryptDecrypt();

    @Override
    public UserProfileDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserProfileDTO user = new UserProfileDTO();
        user.setUserId((edUtil.encrypt(String.valueOf(rs.getInt("pk_user_details_id")))).replaceAll("(\\r|\\n|\\t)", ""));
        user.setUserIdDec(rs.getInt("pk_user_details_id"));

        OrganizationDTO orgData = new OrganizationDTO();
        //orgData.setOrgId((edUtil.encrypt(String.valueOf(rs.getInt("fk_organization_id")))).replaceAll("(\\r|\\n|\\t)", ""));
        orgData.setOrgName(rs.getString("organization_name") != null ? rs.getString("organization_name") : "");
        orgData.setOragnizationId(rs.getInt("fk_organization_id"));
        user.setOrgDetails(orgData);

        UserTypeDTO userTypeData = new UserTypeDTO();
        userTypeData.setUserTypeId((edUtil.encrypt(String.valueOf(rs.getInt("fk_user_type_id")))).replaceAll("(\\r|\\n|\\t)", ""));
        userTypeData.setUserType(rs.getString("user_type"));
        user.setUserTypeDetails(userTypeData);

        DesignationDTO designationData = new DesignationDTO();
        designationData.setDesignationId((edUtil.encrypt(String.valueOf(rs.getInt("designation_id")))).replaceAll("(\\r|\\n|\\t)", ""));
        designationData.setName(rs.getString("designation_name") != null ? rs.getString("designation_name") : "");
        user.setDesignation(designationData);

        user.setUserFullName(rs.getString("full_name") != null ? rs.getString("full_name") : "");
        user.setFirstName(rs.getString("first_name") != null ? rs.getString("first_name") : "");
        user.setTitle(rs.getString("title") != null ? rs.getString("title") : "");
        user.setLastName(rs.getString("last_name") != null ? rs.getString("last_name") : "");
        user.setGender(rs.getString("gender") != null ? rs.getString("gender") : "");
        user.setUserEmail(rs.getString("email"));
        user.setContactNo(rs.getString("contact_no"));
        user.setEmergencyNo(rs.getString("emergency_contact_no"));
        user.setAddress(rs.getString("address"));
        user.setPostcode(rs.getInt("postcode"));

        return user;
    }

}
