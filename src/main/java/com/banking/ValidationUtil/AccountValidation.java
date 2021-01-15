package com.banking.ValidationUtil;

import com.banking.model.Account;

public class AccountValidation {
	public static boolean validation(Account account) {
		if(account==null ||
				account.getAccType()==null ||
				account.getAccType().equalsIgnoreCase("") ||
				account.getAccNum()==null ||
				account.getBalance()==null) 
			return false;
		return true;
	}
}
