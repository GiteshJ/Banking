package com.banking.customException;

public class UserNotFoundException extends Exception{


	private static final long serialVersionUID = -8829427162558193040L;
	private final String message;
	
	public UserNotFoundException(String message) {
		this.message = message;
	} 
	
	@Override
	public String getMessage() {
		return message;
	}
}