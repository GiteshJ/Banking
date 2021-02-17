package com.banking.dto;

import java.io.Serializable;
import java.util.List;

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
public class CustomerDetailsDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3494496794321198392L;
	private String userName;
    private String firstName;
    private String lastName;
    private String adhaarNum;
    private String panNum;
    private List<AccountDetailsDto> accountList;
}
