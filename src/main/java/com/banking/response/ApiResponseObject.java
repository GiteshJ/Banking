package com.banking.response;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public abstract class ApiResponseObject implements Serializable {
	

	private static final long serialVersionUID = 2641807169761075833L;
	private LocalDateTime timestamp;
	private String message;

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ApiResponseObject() {
		this.timestamp = LocalDateTime.now();
	}
	

	
}
