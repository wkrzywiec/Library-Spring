package com.wkrzywiec.spring.library.service;

import java.util.List;

import com.wkrzywiec.spring.library.dto.UserDTO;
import com.wkrzywiec.spring.library.entity.Role;
import com.wkrzywiec.spring.library.entity.User;

public interface UserService {

	boolean isUsernameAlreadyInUse(String username);
	
	boolean isEmailAlreadyInUse(String email);
	
	void saveReaderUser(UserDTO user);
	
	void saveSpecialUser(UserDTO user);
	
	Role getRoleByName(String roleName);
	
	List<User> getAllUsers();
	
	User getUserById(int id);
	
	List<User> searchUsers(String searchText, int pageNo, int resultsPerPage);
	
	int searchUserPagesCount(String searchText, int resultsPerPage);
	
	int searchUsersResultsCount(String searchText);
}
