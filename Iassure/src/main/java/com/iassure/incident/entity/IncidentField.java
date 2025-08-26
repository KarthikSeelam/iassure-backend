package com.iassure.incident.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity@Getter@Setter@Table(name = "incident_fields")
public class IncidentField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "incident_id")
    private Integer incidentId;
    @Column(name = "field_id")
    private Integer fieldId;
    @Column(name = "selected_option")
    private String selectedOption;
    @Column(name = "is_active")
    private Integer isActive;
}
