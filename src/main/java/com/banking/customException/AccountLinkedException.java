package com.banking.customException;

public class AccountLinkedException extends Exception{



	private static final long serialVersionUID = -3463332959794018615L;
	private final String message;
	
	public AccountLinkedException(String message) {
		this.message = message;
	} 
	
	@Override
	public String getMessage() {
		return message;
	}
	
}