package com.wkrzywiec.spring.library.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@EnableWebSecurity
public class LibrarySecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("userDetailService")
	private UserDetailsService libraryUserDetailsService;
	
	@Autowired
	private DataSource dataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(libraryUserDetailsService);
		auth.authenticationProvider(authenticationProvider());
		
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
				.logout().permitAll()
			.and()
				.exceptionHandling().accessDeniedPage("/access-denied")
			.and()
				.rememberMe()
					.rememberMeCookieName("library-spring-remember-me-cookie")
					.tokenRepository(persistentTokenRepository())
					.tokenValiditySeconds(30 * 60 * 24);
	}
	
	
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web
			.ignoring()
				.antMatchers("/register-user")
				.antMatchers("/successful-registration")
				.antMatchers("/forgot-password")
				.antMatchers("/submit-forgot-password")
				.antMatchers("/changePassword*");
	}

	@Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(libraryUserDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
	
	@Bean
	  public PersistentTokenRepository persistentTokenRepository() {
	      JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
	      repo.setDataSource(dataSource);
	      return repo;
	  }

	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
