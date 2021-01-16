package com.banking.ValidationUtil;

import com.banking.dto.UpdateBalanceDto;

public class UpdateBalanceValidation {
	public static boolean validate(UpdateBalanceDto updateBalance) {
		if(updateBalance==null ||
				updateBalance.getAccNum()==null ||
				updateBalance.getBalance()==null)
			return false;
		return true;
	}
}
