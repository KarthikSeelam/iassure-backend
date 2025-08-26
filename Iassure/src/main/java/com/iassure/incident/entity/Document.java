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
@Table(name = "documentrepository")
@Getter
@Setter
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DocumentID")
    private Integer documentId;
    @Column(name = "DocumentName")
    private String documentName;
    @Column(name = "DocumentType")
    private String documentType;
    @Column(name = "DocumentSize")
    private Integer documentSize;
    @Column(name = "DocumentDetails")
    private String documentDetails;
    @Column(name = "DocumentURL")
    private String documentURL;
    @Column(name = "UploadDate")
    private Timestamp uploadDate;
    @Column(name = "OragnizationID")
    private Integer organizationId;
    @Column(name = "UserID")
    private Integer userId;
    @Column(name = "IsActive")
    private Integer isActive;
}