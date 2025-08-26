package com.iassure.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PasswordDTO {

	private String currentPassword;
	
	private String newPassword;
	
	private String confirmPassword;
	
	private String encryptedUserId;
}
