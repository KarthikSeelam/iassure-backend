package com.iassure.incident.dto;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Sravanth T
 *
 */
@Getter
@Setter
public class DocumentDTO {

    private Integer documentId;
    private String documentName;
    private String documentType;
    private Integer documentSize;
    private String documentDetails;
    private String documentURL;
    private Integer organizationId;
    private Integer userId;
    private Integer isActive;
}