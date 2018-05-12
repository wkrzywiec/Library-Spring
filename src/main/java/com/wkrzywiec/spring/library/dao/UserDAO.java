package com.wkrzywiec.spring.library.dao;

import java.util.List;

import com.wkrzywiec.spring.library.entity.Role;
import com.wkrzywiec.spring.library.entity.User;

public interface UserDAO {

	User getActiveUser(String username);
	
	User getActiveUserByEmail(String email);
	
	User getUserById(int id);
	
	void saveUser(User user, String roleName);
	
	Role getRoleByName(String roleName);
	
	List<User> getAllUsers();
	
	List<User> searchUsers(String searchText, int pageNo, int resultsPerPage);
	
	int searchUsersTotalCount(String searchText);
	
	
	
}
