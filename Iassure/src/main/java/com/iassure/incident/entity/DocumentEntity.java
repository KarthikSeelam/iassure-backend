package com.iassure.incident.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Setter
@Getter
@Table(name = "DocumentRepository")
public class DocumentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DocumentID")
    private int documentId;

    @Column(name = "EntityID2")
    private int entityId2;

    @Column(name = "EntityID1")
    private int entityId1;

    @Column(name = "DocumentName")
    private String documentName;

    @Column(name = "DocumentType")
    private String documentType;

    @Column(name = "DocumentSize")
    private int documentSize;

    @Column(name = "DocumentDetails")
    private String documentDetails;

    @Column(name = "DocumentURL")
    private String documentUrl;

    @Column(name = "UploadDate")
    private Timestamp uploadDate;

    @Column(name = "OrganizationID")
    private Integer organizationId;

    @Column(name = "UserID")
    private Integer userId;

    @Column(name = "IsActive")
    private Integer isActive;
}