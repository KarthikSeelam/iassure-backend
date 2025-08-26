package com.iassure.usermanagement.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestLogin {
    private String username;
    private String password;
}
