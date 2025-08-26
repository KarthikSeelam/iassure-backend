package com.iassure.usermanagement.response;

import com.iassure.incident.response.StatusResponse;
import com.iassure.usermanagement.entity.UserDetailsById;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor
public class GetUserByIdResponse {
    private StatusResponse statusResponse;
    private UserDetailsById users;
}
