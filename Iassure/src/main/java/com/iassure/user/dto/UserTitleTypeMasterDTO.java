package com.iassure.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 *
 * @author Sravanth T
 */
@Getter
@Setter
@NoArgsConstructor
public class UserTitleTypeMasterDTO implements Serializable {

    private Integer userTitleTypeId;
    private String name;
}