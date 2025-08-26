package com.iassure.incident.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

/**
 *
 * @author Sravanth T
 *
 */
@Entity
@Table(name = "MasterSource")
@Getter
@Setter
public class MasterSource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SourceID")
    private Integer sourceId;
    @Column(name = "SNo")
    private Integer sNo;
    @Column(name = "SourceName")
    private String sourceName;
    @Column(name = "SourceType")
    private String sourceType;
    @Column(name = "SourceDescription1")
    private String sourceDescription1;
    @Column(name = "SourceDescription2")
    private String sourceDescription2;
    @Column(name = "IsActive")
    private Integer isActive;
    @Column(name = "CreatedAt")
    private Timestamp createdAt;
}