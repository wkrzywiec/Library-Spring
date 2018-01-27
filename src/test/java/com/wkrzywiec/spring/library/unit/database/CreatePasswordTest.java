package com.wkrzywiec.spring.library.unit.database;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CreatePasswordTest {

	public static void main(String[] args){
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println(encoder.encode("test"));
	}
}
