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
public class TransferMoneyDto {
	private Integer accNumSender;
	private Integer accNumReceiver;
	private Float balance;
}
