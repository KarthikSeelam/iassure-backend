package UserDetailsDTO;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class OrganizationDetailsDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long pkOrganizationId;

    private Boolean externalClient;

    private Long docRepositoryId;

    private Long parentOrganizationId;

    private String organizationName;

    private String streetNo;

    private String street;

    private String suburb;

    private String state;

    private Integer postcode;

    private Integer fkCategoryId;

    private String contactNo;

    private Long fkStatusId;

    private Date createdOn;

    private Date updatedOn;

    private Long createdBy;

    private Long updatedBy;

    private String address;

}
