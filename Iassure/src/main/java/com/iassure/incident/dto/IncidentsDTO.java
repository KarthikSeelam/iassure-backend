/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iassure.incident.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 *
 * @author Naveen Kumar Chintala
 */
@Getter
@Setter
public class IncidentsDTO implements Serializable{

    private Integer incidentId;
    private Integer entityId;
    private String fileType;
    private Integer orgId;
    private Integer userId;
    private Integer sourceId;
    private Integer categoryId;
    private String title;
    private String description;
    private Integer severityId;
    private String attachmentUrl;
    private String productName;
    private String issueType;
    private String supplierName;
    private Integer affectedQuantity;
    private String issueArea;
    private String productCode;
    private String batchNo;
    private String comments;
    private Integer assignedUserId;
    private Integer incidentStatusId;
    private Integer aiFlag;
    private String userPrompt;
    private String source;
    private String category;
    private String severity;
    private String department;
    private Integer departmentId;


    @Override
    public String toString() {
        return "IncidentsDTO{" +
                "incidentId=" + incidentId +
                ", orgId=" + orgId +
                ", sourceId=" + sourceId +
                ", categoryId=" + categoryId +
                ", caseSummary='" + title + '\'' +
                ", caseDescription='" + description + '\'' +
                ", severityId=" + severityId +
                ", attachmentUrl='" + attachmentUrl + '\'' +
                ", productName='" + productName + '\'' +
                ", issueType='" + issueType + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", affectedQuantity=" + affectedQuantity +
                ", issueArea='" + issueArea + '\'' +
                ", productCode='" + productCode + '\'' +
                ", batchNo='" + batchNo + '\'' +
                ", assignedUserID=" + assignedUserId +
                ", incidentStatusID=" + incidentStatusId +
                '}';
    }
}
