package com.wkrzywiec.spring.library.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wkrzywiec.spring.library.dao.UserDAO;
import com.wkrzywiec.spring.library.dto.UserDTO;
import com.wkrzywiec.spring.library.entity.Role;
import com.wkrzywiec.spring.library.entity.Roles;
import com.wkrzywiec.spring.library.entity.User;
import com.wkrzywiec.spring.library.entity.UserLog;


@Service("userDetailService")
public class LibraryUserDetailService implements UserDetailsService, UserService {

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		com.wkrzywiec.spring.library.entity.User user = userDAO.getActiveUser(username);
		System.out.println(user.getRoles());
		boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
		
        Collection<? extends GrantedAuthority> authList = getUserAuthorities(user.getRoles());
        
		return new org.springframework.security.core.userdetails.User(
					user.getUsername(),
					user.getPassword(),
					user.isEnable(),
					accountNonExpired,
					credentialsNonExpired,
					accountNonLocked,
					authList)
				;
	}
	
	@Override
	public boolean isUsernameAlreadyInUse(String username){
		
		boolean userInDb = true;
		if (userDAO.getActiveUser(username) == null) userInDb = false;
		return userInDb;
	}
	
	@Override
	public boolean isEmailAlreadyInUse(String email){
		
		boolean userInDb = true;
		if (userDAO.getActiveUserByEmail(email) == null) userInDb = false;
		return userInDb;
	}

	@Override
	public User saveReaderUser(UserDTO user, String changedByUsername) {
		
		User userEntity = convertUserDTOtoUserEntity(user);
		userEntity = userDAO.saveUser(userEntity, Roles.USER.toString(), changedByUsername);
		
		return userEntity;
	}
	
	@Override
	public User saveSpecialUser(UserDTO user, String changedByUsername) {
		User userEntity = convertUserDTOtoUserEntity(user);
		
		String roleName;
		
		if (user.getRole().equals("ADMIN")){
			roleName = Roles.ADMIN.toString();
		} else {
			roleName = Roles.LIBRARIAN.toString();
		}
		
		userEntity = userDAO.saveUser(userEntity, roleName, changedByUsername);
		return userEntity;
	}

	@Override
	public User updateUser(int id, UserDTO userDTO, String changedByUsername) {
		
		User user = this.getUserById(id);
			
		Map<String, String> changedFields = new HashMap<String, String>();
		
		if (!userDTO.getEmail().isEmpty()) {
			if (user.getEmail() == null) {
				changedFields.put("email", userDTO.getEmail());
			} else {
				if (!user.getEmail().equals(userDTO.getEmail())){
					changedFields.put("email", userDTO.getEmail());
				}
			}
		}
		
		if (!userDTO.getFirstName().isEmpty()) {
			if (user.getFirstName() == null) {
				changedFields.put("firstName", userDTO.getFirstName());
			} else {
				if (!user.getFirstName().equals(userDTO.getFirstName())) {
					changedFields.put("firstName", userDTO.getFirstName());
				}
			}
		}
	
		if (!userDTO.getLastName().isEmpty()) {
			if (user.getLastName() == null) {
				changedFields.put("lastName", userDTO.getLastName());
			} else {
				if (!user.getLastName().equals(userDTO.getLastName())) {
					changedFields.put("lastName", userDTO.getLastName());
				}
			}
		}
		
		if (!userDTO.getPhone().isEmpty()) {
			if (user.getPhone() == null) {
				changedFields.put("phone", userDTO.getPhone());
			} else {
				if (!user.getPhone().equals(userDTO.getPhone())) {
					changedFields.put("phone", userDTO.getPhone());
				}
			}
		}
		
		if (!userDTO.getAddress().isEmpty()) {
			if (user.getAddress() == null) {
				changedFields.put("address", userDTO.getAddress());
			} else {
				if (!user.getAddress().equals(userDTO.getAddress())) {
					changedFields.put("address", userDTO.getAddress());
				}
			}
		}
		
		if (!userDTO.getPostalCode().isEmpty()) {
			if (user.getPostalCode() == null) {
				changedFields.put("postalCode", userDTO.getPostalCode());
			} else {
				if (!user.getPostalCode().equals(userDTO.getPostalCode())) {
					changedFields.put("postalCode", userDTO.getPostalCode());
				}
			}
		}
		
		if (!userDTO.getCity().isEmpty()) {
			if (user.getCity() == null) {
				changedFields.put("city", userDTO.getCity());
			} else {
				if (!user.getCity().equals(userDTO.getCity())) {
					changedFields.put("city", userDTO.getCity());
				}
			}
		}
		user = userDAO.updateUser(id, changedFields, changedByUsername);
		
		return user;
	}
	
	@Override
	public User enableUser(int id, String changedByUsername) {
		
		User user = this.getUserById(id);
		if (!user.isEnable()) {
			user = userDAO.enableUser(id, changedByUsername);
		}
		
		return user;
	}

	@Override
	public User disableUser(int id, String changedByUsername) {
		
		User user = this.getUserById(id);
		if (user.isEnable()) {
			user = userDAO.disableUser(id, changedByUsername);
		}
		
		return user;
	}

	@Override
	public Role getRoleByName(String roleName) {
		return userDAO.getRoleByName(roleName);
	}
	
	
	@Override
	public List<User> getAllUsers() {
		return userDAO.getAllUsers();
	}
	
	@Override
	public User getUserById(int id) {
		return userDAO.getUserById(id);
	}
	
	

	@Override
	public User getUserByUsername(String username) {
		
		return userDAO.getActiveUser(username);
	}

	@Override
	public List<User> searchUsers(String searchText, int pageNo,
			int resultsPerPage) {
		
		List<User> userList = userDAO.searchUsers(searchText, pageNo, resultsPerPage);
		
		return userList;
	}

	@Override
	public int searchUserPagesCount(String searchText, int resultsPerPage) {
		
		long userCount = searchUsersResultsCount(searchText);
		int pageCount = (int) Math.floorDiv(userCount, resultsPerPage) + 1;
		
		return pageCount;
	}

	@Override
	public int searchUsersResultsCount(String searchText) {
		
		int userCount = userDAO.searchUsersTotalCount(searchText);
		
		return userCount;
	}
	
	@Override
	public List<UserLog> getUserLogs(int id) {
		
		return userDAO.getUserLogs(id);
	}

	private Collection<? extends GrantedAuthority> getUserAuthorities(Set<Role> modelAuthSet) {
		
		List<Role> modelAuthList = convertRolesSetToList(modelAuthSet);
		
        List<GrantedAuthority> authList = getGrantedUserAuthority(modelAuthList);
        return authList;
    }

	private List<Role> convertRolesSetToList(Set<Role> modelAuthSet) {
		return new ArrayList<Role>(modelAuthSet);
	}
	
	private List<GrantedAuthority> getGrantedUserAuthority (List<Role> modelAuthList){
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		for (Role auth : modelAuthList){
			authorities.add(new SimpleGrantedAuthority(auth.getName()));
		}
		
		return authorities;
	}
	
	private User convertUserDTOtoUserEntity(UserDTO user) {
		
		User userEntity = new User();
		
		userEntity.setUsername(user.getUsername());
		userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
		userEntity.setEmail(user.getEmail());
		userEntity.setEnable(true);
		userEntity.setFirstName(user.getFirstName());
		userEntity.setLastName(user.getLastName());
		userEntity.setPhone(user.getPhone());
		userEntity.setBirthday(user.getBirthday());
		userEntity.setAddress(user.getAddress());
		userEntity.setPostalCode(user.getPostalCode());
		userEntity.setCity(user.getCity());
		
		return userEntity;
	}
}
