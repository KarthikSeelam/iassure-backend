package com.iassure.payload;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Naveen Kumar Chintala
 */
@Getter
@Setter
public class SignUpRequest {

    private String name;
    private String mobile;
    private String email;
    private String password;



}
