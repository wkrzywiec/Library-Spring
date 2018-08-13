package com.wkrzywiec.spring.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.wkrzywiec.spring.library.entity.User;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender emailSender;
	
	@Override
	public void sendForgotPasswordEmail(User user, String path, String token) {
		
		SimpleMailMessage message = new SimpleMailMessage(); 
	    message.setTo(user.getEmail()); 
	    message.setSubject("Reset Password"); 
	    message.setText(this.prepareForgotPasswordEmail(user, path, token));
	    emailSender.send(message);
		
	}

	private String prepareForgotPasswordEmail(User user, String path, String token) {
		
		String url = path + "?userId=" + user.getId() + "&token=" + token;
		String text = "Hello " + user.getFirstName() + ","
				+ "\n\nWe have receive your request for reseting password to your acoount into our Library Portal" 
				+ "\n\n If you have not requested it please let us know immediently."
				+ "\n Here is your personal link to a page where you can reset your password: "
				+ "\n\n" + url;
		
		return text;
	}
}
