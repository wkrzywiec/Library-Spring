package com.wkrzywiec.spring.library.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;

@Configuration
@EnableWebSecurity
public class LibrarySecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		UserBuilder users = User.withDefaultPasswordEncoder();
		
		auth.inMemoryAuthentication()
			.withUser(users.username("admin").password("admin").roles("LIBRARIAN"))
			.withUser(users.username("test").password("test").roles("READER"));
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
	
		http.authorizeRequests()
				.anyRequest().authenticated()
			.and()
				.formLogin()
					.loginPage("/loginPage")
					.loginProcessingUrl("/login")
					.permitAll()
					.and()
					.logout().permitAll();
		
	}


}
