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
@Table(name = "repository")
@Getter
@Setter
public class RepositoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_repository_id")
    private Integer pkRepositoryId;
    @Column(name = "fk_organization_id")
    private Integer organizationId;
    @Column(name = "parent_id")
    private Integer parentId;
    @Column(name = "directory_name")
    private String directoryName;
    @Column(name = "location")
    private String location;
    @Column(name = "fk_status_id")
    private Integer statusId;
    @Column(name = "is_public")
    private Integer isPublic;
    @Column(name = "created_on")
    private Timestamp createdTime;
    @Column(name = "created_by")
    private int createdBy;
    @Column(name = "updated_on")
    private Timestamp updatedTime;
    @Column(name = "updated_by")
    private int updatedBy;
}