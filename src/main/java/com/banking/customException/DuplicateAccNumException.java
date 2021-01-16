package com.banking.customException;

public class DuplicateAccNumException extends Exception{


	private static final long serialVersionUID = 3342134875333695958L;
	private final String message;
	
	public DuplicateAccNumException(String message) {
		this.message = message;
	} 
	
	@Override
	public String getMessage() {
		return message;
	}
	
}