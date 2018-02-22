package com.wkrzywiec.spring.library.service;

import com.wkrzywiec.spring.library.dto.UserDTO;
import com.wkrzywiec.spring.library.entity.Role;

public interface UserService {

	public boolean isUsernameAlreadyInUse(String username);
	
	public boolean isEmailAlreadyInUse(String email);
	
	public void saveReaderUser(UserDTO user);
	
	public Role getRoleByName(String roleName);
}
