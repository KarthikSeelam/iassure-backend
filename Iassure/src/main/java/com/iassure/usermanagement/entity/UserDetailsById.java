package com.iassure.usermanagement.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity@Getter@Setter
public class UserDetailsById {
        @Id
        @Column(name = "UserID")
        private Integer userID;

        @Column(name = "FirstName")
        private String firstName;

        @Column(name = "LastName")
        private String lastName;

        @Column(name = "ContactNumber")
        private String contactNumber;

        @Column(name = "UserName")
        private String userName;

        @Column(name = "GenderID")
        private Integer genderId;

        @Column(name = "GenderName")
        private String genderName;

        @Column(name = "TitleID")
        private Integer titleId;

        @Column(name = "TitleName")
        private String titleName;

        @Column(name = "UserTypeID")
        private Integer userTypeId;

        @Column(name = "UserType")
        private String userType;

        @Column(name = "DepartmentID")
        private Integer departmentId;

        @Column(name = "DepartmentName")
        private String departmentName;




}
