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

/**
 *
 * @author rjanumpally
 */
@Getter
@Setter
@NoArgsConstructor
public class UserTypeDTO implements Serializable {

    private String userTypeId;
    private String userType;
    private String description;

}
