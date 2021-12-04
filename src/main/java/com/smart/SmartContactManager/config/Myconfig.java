package com.smart.SmartContactManager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class Myconfig extends WebSecurityConfigurerAdapter{
	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailServiceImpl();
	}	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		daoAuthenticationProvider.setUserDetailsService(userDetailsService());
		return daoAuthenticationProvider;
	}
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {	
		auth.authenticationProvider(daoAuthenticationProvider());
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		 http.csrf()
		.disable()
		.cors()
		.disable()
		.authorizeHttpRequests()
		.antMatchers("/admin/**")
		.hasRole("ADMIN")
		.antMatchers("/user/**")
		.hasRole("USER")
		.antMatchers("/**")
		.permitAll()
		.and()
		.formLogin()
		.loginPage("/signin")
		.defaultSuccessUrl("/user/dashboard")
		.failureUrl("/signin");
	}
	 
}
