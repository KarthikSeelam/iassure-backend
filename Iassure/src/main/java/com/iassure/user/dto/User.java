package com.iassure.user.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Naveen Kumar Chintala
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User  {

    private Integer id;
    private String name;
    private String email;
    private String password;
    private String mobile;
    private Set<Role> roles = new HashSet<>();



}
