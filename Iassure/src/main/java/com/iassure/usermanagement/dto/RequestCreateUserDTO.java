package com.iassure.usermanagement.dto;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class RequestCreateUserDTO {
    private int title;
    private int userId;
    private int duMappingId;
    private String flag;
    private int userTypeId;
    private int orgId;
    private int departmentId;
    private String firstName;
    private String lastName;
    private int gender;
    private String username;
    private String contactNumber;
    private int createdBy;

}
