package com.banking.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SpringAppConfigSec extends WebSecurityConfigurerAdapter {

  @Override
  public void configure(AuthenticationManagerBuilder authentication)
          throws Exception
  {
      authentication.inMemoryAuthentication()
              .withUser("gitesh")
              .password("gitesh")
              .roles("ADMIN")
              .and()
              .withUser("githash")
              .password("githash")
              .roles("USER");
      
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception{
  	http.csrf().disable();
  	http.authorizeRequests().antMatchers("/sec/**").fullyAuthenticated().and().httpBasic();
  }
  
  
//  @Bean
//	public static NoOpPasswordEncoder passwordEncoder() {
//		return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
//	}
//
}