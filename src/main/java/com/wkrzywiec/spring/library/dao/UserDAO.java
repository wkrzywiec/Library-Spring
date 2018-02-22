package com.wkrzywiec.spring.library.dao;

import com.wkrzywiec.spring.library.entity.Role;
import com.wkrzywiec.spring.library.entity.User;

public interface UserDAO {

	public User getActiveUser(String username);
	
	public User getActiveUserByEmail(String email);
	
	public void saveUser(User user);
	
	public Role getRoleByName(String roleName);
	
}
