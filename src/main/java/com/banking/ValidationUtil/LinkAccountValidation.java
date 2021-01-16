package com.banking.ValidationUtil;

import com.banking.dto.LinkAccountDto;

public class LinkAccountValidation {
	public static boolean validate(LinkAccountDto linkAccountDto) {
		if(linkAccountDto==null ||
				linkAccountDto.getAccNum()==null ||
				linkAccountDto.getUsername()==null)
			return false;
		return true;
	}
}
