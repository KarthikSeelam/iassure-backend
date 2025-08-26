package com.iassure.incident.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter@Entity
public class GetIncidentChatDetails {
    @Id
    @Column(name = "IncidentChatID")
    private Integer incidentChatId;

    @Column(name = "IncidentChat")
    private String comments;

    @Column(name = "UserID")
    private Integer userId;

    @Column(name = "IncidentID")
    private Integer incidentId;

    @Column(name = "CreatedOn")
    private String createdOn;

    @Column(name = "FullName")
    private String username;
}
