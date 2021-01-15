package com.banking.auth.model;

import java.util.HashSet;

public class TokenBlacklistSet {

	public HashSet<String> blackListedTokens;
	
	public TokenBlacklistSet() {
		this.blackListedTokens  = new HashSet<>();
	}
}
