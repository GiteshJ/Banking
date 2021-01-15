package com.banking.config;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.banking.auth.model.TokenBlacklistSet;

@Configuration
public class LogoutTokenConfig {

	@Bean
	@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
	public TokenBlacklistSet getBlackListToken() {
		return new TokenBlacklistSet();
	}
}
