package com.iassure.incident.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 *
 * @author Sravanth T
 */
@Getter
@Setter
public class SupplierDTO implements Serializable {

    private Integer supplierId;
    private Integer organizationId;
    private String supplierName;
    private String description;
    private String contactNo;
    private String email;
    private String address;
    private String streetNo;
    private String street;
    private String suburb;
    private String state;
    private Integer postcode;
    private String country;
    private Integer statusId;
}