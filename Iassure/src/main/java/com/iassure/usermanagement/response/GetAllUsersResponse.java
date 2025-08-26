package com.iassure.usermanagement.response;

import com.iassure.incident.entity.Users;
import com.iassure.incident.response.StatusResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter@Setter@NoArgsConstructor@AllArgsConstructor
public class GetAllUsersResponse {
    private StatusResponse statusResponse;
    private int totalUsers;
    private List<Users> users;
}
