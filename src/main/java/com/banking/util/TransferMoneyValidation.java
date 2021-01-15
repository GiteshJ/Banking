package com.banking.util;

import com.banking.dto.TransferMoneyDto;

public class TransferMoneyValidation {
	public static boolean validate(TransferMoneyDto transferMoneyDto) {
		if(transferMoneyDto==null ||
			transferMoneyDto.getAccNumReceiver()==null||
			transferMoneyDto.getAccNumSender()==null||
			transferMoneyDto.getBalance()==null)
			return false;
		return true;
	}
}
