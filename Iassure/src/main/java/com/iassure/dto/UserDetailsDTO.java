package com.iassure.dto;


import com.iassure.entity.OrganizationDetails;
import com.iassure.entity.UserTypeMaster;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Date;

@Data
public class UserDetailsDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer userId;

    private Integer organizationId;

    private UserTypeMaster userTypeMaster;

    private OrganizationDetails organizationDetails;

    private String employeeId;

    private String fullName;

    private String firstName;

    private String title;

    private String lastName;

    private String gender;

    private String email;

    private String contactNo;

    private String emergencyContactNo;

    private String address;

    private String streetNo;

    private String street;

    private String suburb;

    private String state;

    private Long postcode;

    private Date inductionDate;

    private Integer designation;

    private String username;

    private String password;

    private Boolean loginRequired;

    private String ipAddress;

    private java.util.Date lastLoggedIn;

    private Boolean firstTime;

    private Integer fkStatusId;

    private java.util.Date createdOn;

    private java.util.Date updatedOn;

    private Integer createdBy;

    private Integer updatedBy;


}
