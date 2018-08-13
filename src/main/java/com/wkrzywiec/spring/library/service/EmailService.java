package com.wkrzywiec.spring.library.service;

import com.wkrzywiec.spring.library.entity.User;

public interface EmailService {
	
	void sendUserRegistrationConfirmEmail(User user);
	
	void sendForgotPasswordEmail(User user, String path, String token);

}
