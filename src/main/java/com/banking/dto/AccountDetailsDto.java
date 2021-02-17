package com.banking.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDetailsDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5141707953830840535L;
	private Integer accNum;
    private Double balance;
    private String accType;
}
