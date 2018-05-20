package com.wkrzywiec.spring.library.dao;

import java.util.List;
import java.util.Map;

import com.wkrzywiec.spring.library.entity.Role;
import com.wkrzywiec.spring.library.entity.User;
import com.wkrzywiec.spring.library.entity.UserLog;

public interface UserDAO {

	User getActiveUser(String username);
	
	User getActiveUserByEmail(String email);
	
	User getUserById(int id);
	
	User saveUser(User user, String roleName, String changedByUsername);
	
	User updateUser(int id, Map<String, String> changedFields, String changedByUsername);
	
	User enableUser(int id, String changedByUsername);
	
	User disableUser(int id, String changedByUsername);
	
	Role getRoleByName(String roleName);
	
	List<User> getAllUsers();
	
	List<User> searchUsers(String searchText, int pageNo, int resultsPerPage);
	
	int searchUsersTotalCount(String searchText);
	
	List<UserLog> getUserLogs(int id);
	
}
