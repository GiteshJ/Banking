package com.banking.response;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ApiEntity extends ApiResponseObject {

	private Object data;

	private String message;

	

	public ApiEntity(String message, Object data) {
		super();
		setMessage(message);
		this.data = data;
	}
	

	public ApiEntity(String message) {
		setMessage(message);
	}

	public ApiEntity(Object data) {
		super();
		this.data = data;
	}

}
