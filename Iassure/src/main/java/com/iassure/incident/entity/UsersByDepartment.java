package com.iassure.incident.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class UsersByDepartment {
    @Id
    @Column(name = "UserID")
    private Integer userId;
    @Column(name = "UserName")
    private String userName;
    @Column(name = "EmailAddress")
    private String emailAddress;
    @Column(name = "DepartmentID")
    private Integer departmentId;
    @Column(name = "DepartmentName")
    private String departmentName;
}
