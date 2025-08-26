package com.iassure.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "user_details")
public class UserDetails implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_user_details_id", nullable = false)
    private Integer userId;

    @Column(name = "fk_organization_id", nullable = false)
    private Integer organizationId;

    @ManyToOne
    @JoinColumn(name = "fk_user_type_id", referencedColumnName = "pk_user_type_id")
    private UserTypeMaster userTypeMaster;

    @Column(name = "employee_id")
    private String employeeId;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "title")
    private String title;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "gender")
    private String gender;

    @Column(name = "email")
    private String email;

    @Column(name = "contact_no")
    private String contactNo;

    @Column(name = "emergency_contact_no")
    private String emergencyContactNo;

    @Column(name = "address")
    private String address;

    @Column(name = "street_no")
    private String streetNo;

    @Column(name = "street")
    private String street;

    @Column(name = "suburb")
    private String suburb;

    @Column(name = "state")
    private String state;

    @Column(name = "postcode")
    private Integer postcode;

    @Column(name = "induction_date")
    private Date inductionDate;

    @Column(name = "designation")
    private Integer designation;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "is_login_required")
    private Boolean loginRequired;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "last_logged_in")
    private Date lastLoggedIn;

    @Column(name = "is_first_time")
    private Boolean firstTime;

    @Column(name = "fk_status_id", nullable = false)
    private Integer fkStatusId;

    @Column(name = "created_on")
    private Date createdOn;

    @Column(name = "updated_on")
    private Date updatedOn;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "updated_by")
    private Integer updatedBy;

}
