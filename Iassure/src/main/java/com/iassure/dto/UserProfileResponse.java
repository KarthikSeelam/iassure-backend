package com.iassure.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class UserProfileResponse implements Serializable {


        @Serial
        private static final long serialVersionUID = 1L;

        private Integer pkUserDetailsId;

        private Integer fkOrganizationId;

        private String fullName;

        private String firstName;

        private String title;

        private String lastName;

        private String gender;

        private String email;

        private Boolean firstTime;

    }
