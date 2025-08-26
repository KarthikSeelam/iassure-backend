package com.iassure.usermanagement.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter@Entity@Table(name = "Users")
public class UsersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID")
    private Integer userId;

    @Column(name = "OrgID")
    private Integer orgId;

    @Column(name = "DepartmentID")
    private Integer departmentId;

    @Column(name = "UserTypeID", nullable = false)
    private Integer userTypeId;

    @Column(name = "Title")
    private Integer title;

    @Column(name = "FirstName")
    private String firstName;

    @Column(name = "LastName")
    private String lastName;

    @Column(name = "FullName")
    private String fullName;

    @Column(name = "Gender")
    private Integer gender;

    @Column(name = "UserName")
    private String username;

    @Column(name = "Password")
    private String password;

    @Column(name = "LoginRequired")
    private Integer loginRequired;

    @Column(name = "IPAddress")
    private String ipAddress;

    @Column(name = "LastLoginDate")
    private String lastLoginDate;

    @Column(name = "ContactNumber")
    private String contactNumber;

    @Column(name = "LoginFirstTime")
    private Integer loginFirstTime;

    @Column(name = "IsActive")
    private Integer isActive;

    @Column(name = "CreatedBy")
    private Integer createdBy;

    @Column(name = "UpdatedBy")
    private Integer updatedBy;

    @Column(name = "CreatedAt")
    private String createdAt;

    @Column(name = "UpdatedAt")
    private String updatedAt;
}
