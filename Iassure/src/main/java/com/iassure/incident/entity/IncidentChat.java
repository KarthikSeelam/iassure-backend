package com.iassure.incident.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter@Entity@Table(name = "IncidentChat")
public class IncidentChat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
}
