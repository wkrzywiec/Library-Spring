package com.wkrzywiec.spring.library.service;

import com.wkrzywiec.spring.library.entity.User;

public interface EmailService {
	
	void sendForgotPasswordEmail(User user, String path, String token);
}
