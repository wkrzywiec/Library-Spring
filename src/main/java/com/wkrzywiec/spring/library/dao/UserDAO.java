package com.wkrzywiec.spring.library.dao;

import com.wkrzywiec.spring.library.entity.User;

public interface UserDAO {

	public User getActiveUser(String username);
	
}
