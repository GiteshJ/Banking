package com.banking.util;

import com.banking.dto.UserDto;

public class UserValidation {

		
	public static boolean validate(UserDto user) {
		if(user.getUserName()== null ||
				user.getPassword()==null ||
				user.getFirstName()==null ||
				user.getLastName() == null) return false;
		return true;
	}
	
}
