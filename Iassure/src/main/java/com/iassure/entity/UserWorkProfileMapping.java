package com.iassure.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "user_work_profile_mapping")
public class UserWorkProfileMapping implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_user_work_profile_mapping_id", nullable = false)
    private Integer pkUserWorkProfileMappingId;

    @Column(name = "fk_user_details_id", nullable = false)
    private Integer fkUserDetailsId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_customer_id", referencedColumnName = "pk_organization_id")
    private OrganizationDetails organizationDetails;

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
