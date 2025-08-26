package com.iassure.usermanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
@Entity
@Table(name = "DepartmentUserMapping")
public class DepartmentUserMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DUMappingID")
    private Integer duMappingId;

    @Column(name = "UserID")
    private Integer userId;

    @Column(name = "DepartmentID")
    private Integer departmentId;

    @Column(name = "CreatedAt")
    private String createdAt;
}
