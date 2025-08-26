package com.iassure.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * Type of User
 */
@Data
@Entity
@Table(name = "user_type_master")
public class UserTypeMaster implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_user_type_id", nullable = false)
    private Integer userTypeId;

    @Column(name = "user_type", nullable = false)
    private String userType;

    @Column(name = "description")
    private String description;

    @Column(name = "fk_status_id", nullable = false)
    private Integer statusId;

    @Column(name = "created_on")
    private Date createdOn;

    @Column(name = "updated_on")
    private Date updatedOn;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "updated_by")
    private Integer updatedBy;

}
