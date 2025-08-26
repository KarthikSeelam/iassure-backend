package com.iassure.usermanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter@Entity
public class UserLogin {
    @Id
    @Column(name = "UserID")
    private Integer userId;

    @Column(name = "UserTypeID")
    private Integer userTypeId;

    @Column(name = "DepartmentID")
    private Integer departmentId;

    @Column(name = "FirstName")
    private String firstName;

    @Column(name = "LastName")
    private String lastName;

    @Column(name = "ContactNumber")
    private String contactNumber;

    @Column(name = "UserName")
    private String userName;

    @Column(name = "LoginFirstTime")
    private Integer loginFirstTime;
}
