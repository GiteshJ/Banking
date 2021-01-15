package com.banking.util;

import com.banking.dto.KycDetailsDto;

public class KYCValidation {

	public static boolean validate(KycDetailsDto kycDetailsDto) {
		if(kycDetailsDto==null ||
				kycDetailsDto.getUserName()==null ||
				kycDetailsDto.getAdhaarNum()==null ||
				kycDetailsDto.getPanNum()==null)
			return false;
		return true;
	}
}
