/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iassure.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rjanumpally
 */
@Getter
@Setter
@NoArgsConstructor
public class UserWorkProfileDTO implements Serializable {

    private Integer userWorkProfileId;
    private UserProfileDTO userDetails;
    private OrganizationDTO orgDetails;
    private StatusMasterDTO statusDetails;
    private Integer userDetailsId;
    private String inductionDate;
    private Integer statusId;
    private List<Integer> customerList = new ArrayList<>();
}
