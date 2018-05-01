package com.wkrzywiec.spring.library.dao;

import java.util.List;

import com.wkrzywiec.spring.library.entity.Role;
import com.wkrzywiec.spring.library.entity.User;

public interface UserDAO {

	public User getActiveUser(String username);
	
	public User getActiveUserByEmail(String email);
	
	public void saveUser(User user);
	
	public Role getRoleByName(String roleName);
	
	public List<User> getAllUsers();
	
	public List<User> searchUsers(String searchText, int pageNo, int resultsPerPage);
	
	public int searchUsersTotalCount(String searchText);
	
	
	
}
