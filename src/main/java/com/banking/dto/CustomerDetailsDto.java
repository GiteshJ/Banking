package com.banking.dto;

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
public class CustomerDetailsDto {
	private String userName;
    private String firstName;
    private String lastName;
    private String adhaarNum;
    private String panNum;
    private List<AccountDetailsDto> accountList;
}
