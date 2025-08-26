/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iassure.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 *
 * @author rjanumpally
 */
@JsonInclude(Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
public class UserProfileDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String userId;
    private OrganizationDTO orgDetails;
    private String userFullName;
    private String firstName;
    private String title;
    private String lastName;
    private String empId;
    private String gender;
    private String userEmail;
    private String contactNo;
    private String emergencyNo;
    private String address;
    private Integer postcode;
    private UserTypeDTO userTypeDetails;
    private Integer isFirstTime;
    private DesignationDTO designation;
    private Integer userIdDec;
}
