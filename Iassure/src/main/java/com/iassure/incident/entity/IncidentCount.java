package com.iassure.incident.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class IncidentCount {
    @Id
    @Column(name = "Total")
    private Integer total;
    @Column(name = "TotalImg")
    private String totalImage;
    @Column(name = "Resolved")
    private Integer resolved;
    @Column(name = "ResolvedImg")
    private String resolvedImage;
    @Column(name = "Open")
    private Integer open;
    @Column(name = "OpenImg")
    private String openImage;
    @Column(name = "InProgress")
    private Integer inProgress;
    @Column(name = "PendingImg")
    private String pendingImg;
}
