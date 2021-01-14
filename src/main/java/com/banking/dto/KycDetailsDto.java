package com.banking.dto;

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
public class KycDetailsDto {
	
	
	private String adhaarNum;
    private String panNum;
    private String userName;
    
}
