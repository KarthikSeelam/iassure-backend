package com.iassure.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class StatusMasterDTO implements Serializable {

    private Integer statusId;
    private String status;
    private String description;

}