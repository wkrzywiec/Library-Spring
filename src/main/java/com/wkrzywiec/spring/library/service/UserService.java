package com.wkrzywiec.spring.library.service;

import java.util.List;

import com.wkrzywiec.spring.library.dto.UserDTO;
import com.wkrzywiec.spring.library.entity.Role;
import com.wkrzywiec.spring.library.entity.User;

public interface UserService {

	public boolean isUsernameAlreadyInUse(String username);
	
	public boolean isEmailAlreadyInUse(String email);
	
	public void saveReaderUser(UserDTO user);
	
	public Role getRoleByName(String roleName);
	
	public List<User> getAllUsers();
	
	public List<User> searchUsers(String searchText, int pageNo, int resultsPerPage);
	
	public int searchUserPagesCount(String searchText, int resultsPerPage);
	
	public int searchUsersResultsCount(String searchText);
}
