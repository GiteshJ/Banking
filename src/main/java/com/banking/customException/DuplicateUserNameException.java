package com.banking.customException;

public class DuplicateUserNameException extends Exception{

	private static final long serialVersionUID = 112392253812903008L;
	private final String message;
	
	public DuplicateUserNameException(String message) {
		this.message = message;
	} 
	
	@Override
	public String getMessage() {
		return message;
	}
	
}
