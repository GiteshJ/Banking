package com.banking.customException;

public class InsufficientBalanceException extends Exception{



	private static final long serialVersionUID = -2201347100569742386L;
	private final String message;
	
	public InsufficientBalanceException(String message) {
		this.message = message;
	} 
	
	@Override
	public String getMessage() {
		return message;
	}
	
}