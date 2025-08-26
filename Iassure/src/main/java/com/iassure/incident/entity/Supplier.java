package com.iassure.incident.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

/**
 *
 * @author Sravanth T
 *
 */
@Entity
@Table(name = "suppliers_master")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_supplier_id")
    private Integer pkSupplierId;
    @Column(name = "fk_organization_id")
    private Integer organizationId;
    @Column(name = "supplier_name")
    private String supplierName;
    @Column(name = "description")
    private String description;
    @Column(name = "contact_no")
    private String contactNo;
    @Column(name = "email")
    private String email;
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
    @Column(name = "country")
    private String country;
    @Column(name = "fk_status_id")
    private Integer statusId;
    @Column(name = "created_on")
    private Timestamp createdTime;
    @Column(name = "created_by")
    private int createdBy;
    @Column(name = "updated_on")
    private Timestamp updatedTime;
    @Column(name = "updated_by")
    private int updatedBy;
}