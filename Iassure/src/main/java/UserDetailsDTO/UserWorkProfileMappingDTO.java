package UserDetailsDTO;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserWorkProfileMappingDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long pkUserWorkProfileMappingId;

    private Long fkUserDetailsId;

    private Long fkCustomerId;

    private Long fkStatusId;

    private Date createdOn;

    private Date updatedOn;

    private Long createdBy;

    private Long updatedBy;

}
