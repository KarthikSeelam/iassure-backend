package com.iassure.usermanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "UserTypes")
public class UserTypes {
    @Id
    @Column(name = "UserTypeID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userTypeId;

    @Column(name = "UserType")
    private String userType;

    @Column(name = "Description")
    private String description;


}
