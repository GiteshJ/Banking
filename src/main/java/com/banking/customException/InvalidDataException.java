package com.banking.customException;

public class InvalidDataException extends Exception{

	private static final long serialVersionUID = 6394083959741582614L;
	
	private final String message;
	
	public InvalidDataException(String message) {
		this.message = message;
	} 
	
	@Override
	public String getMessage() {
		return message;
	}
	
}
