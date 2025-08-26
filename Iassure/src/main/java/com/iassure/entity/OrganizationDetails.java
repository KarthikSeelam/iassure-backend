package com.iassure.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "organization_details")
public class OrganizationDetails implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_organization_id", nullable = false)
    private Integer organizationId;

    @Column(name = "is_external_client", nullable = false)
    private Boolean externalClient;

    @Column(name = "doc_repository_id", nullable = false)
    private Integer docRepositoryId;

    @Column(name = "parent_organization_id", nullable = false)
    private Integer parentOrganizationId;

    @Column(name = "organization_name", nullable = false)
    private String organizationName;

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

    @Column(name = "fk_category_id")
    private Integer fkCategoryId;

    @Column(name = "contact_no", nullable = false)
    private String contactNo;

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

    @Column(name = "address", nullable = false)
    private String address;

}
