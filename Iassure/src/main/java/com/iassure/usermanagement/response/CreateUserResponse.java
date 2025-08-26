package com.iassure.usermanagement.response;

import com.iassure.incident.response.StatusResponse;
import com.iassure.usermanagement.entity.UsersEntity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserResponse {
    private StatusResponse statusResponse;
    private UsersEntity users;
}
