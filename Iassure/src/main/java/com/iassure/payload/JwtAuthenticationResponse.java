package com.iassure.payload;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Naveen Kumar Chintala
 */
@Getter
@Setter
public class JwtAuthenticationResponse {

    private String accessToken;
    private String tokenType = "Bearer";

    public JwtAuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
    }


}
