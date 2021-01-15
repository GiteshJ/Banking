package com.banking.ValidationUtil;

import com.banking.dto.BankStatementDto;

public class BankStatementValidation {
	public static boolean validate(BankStatementDto dankStatement) {
		if(dankStatement==null ||
				dankStatement.getAccNum()==null ||
				dankStatement.getEndDate()==null ||
				dankStatement.getStartDate()==null)
			return false;
		return true;
	}
}
