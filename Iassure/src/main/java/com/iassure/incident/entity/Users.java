package com.iassure.incident.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter@Entity
public class Users {
    @Id
    @Column(name = "UserID")
    private Integer userId;
    @Column(name = "FullName")
    private String fullName;
    @Column(name = "UserName")
    private String userName;
    @Column(name = "UserType")
    private String userType;
    @Column(name = "DepartmentName")
    private String departmentName;
}
