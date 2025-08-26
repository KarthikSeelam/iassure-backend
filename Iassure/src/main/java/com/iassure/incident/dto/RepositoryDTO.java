package com.iassure.incident.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 *
 * @author Sravanth T
 */
@Getter
@Setter
public class RepositoryDTO implements Serializable {

    private Integer repositoryId;
    private Integer organizationId;
    private Integer parentId;
    private String directoryName;
    private String location;
    private Integer statusId;
    private Integer isPublic;
}