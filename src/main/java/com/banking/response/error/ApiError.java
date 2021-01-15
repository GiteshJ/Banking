package com.banking.response.error;


import java.util.List;

import com.banking.response.ApiResponseObject;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ApiError extends ApiResponseObject{

	private String debugMessage;

	public ApiError() {
		super();
	}
	
	public ApiError(String message) {
		super();
		setMessage(message);
	}

	public ApiError(Throwable ex) {
		super();
		setMessage("Error");
		this.debugMessage = "";
	}

	public ApiError(String message, Throwable ex) {
		super();
		setMessage(message);
		this.debugMessage = "";
	}

}

