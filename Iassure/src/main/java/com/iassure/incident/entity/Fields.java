package com.iassure.incident.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter@Entity@Table(name = "fields")
public class Fields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "field_data")
    private String fieldData;

    @Column(name = "is_active")
    private Integer isActive;

}
