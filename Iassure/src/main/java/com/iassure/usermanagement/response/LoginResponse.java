package com.iassure.usermanagement.response;

import com.iassure.incident.response.StatusResponse;
import com.iassure.usermanagement.entity.UserLogin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@NoArgsConstructor@AllArgsConstructor
public class LoginResponse {
    private StatusResponse statusResponse;
    private UserLogin userLoginDetails;

}
