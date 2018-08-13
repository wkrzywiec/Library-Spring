package com.wkrzywiec.spring.library.service;

import java.sql.Timestamp;
import java.util.ArrayList;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wkrzywiec.spring.library.dao.UserDAO;
import com.wkrzywiec.spring.library.dto.PasswordDTO;
import com.wkrzywiec.spring.library.dto.UserDTO;
import com.wkrzywiec.spring.library.dto.UserLogDTO;
import com.wkrzywiec.spring.library.entity.Role;
import com.wkrzywiec.spring.library.entity.Roles;
import com.wkrzywiec.spring.library.entity.User;
import com.wkrzywiec.spring.library.entity.UserLog;
import com.wkrzywiec.spring.library.entity.UserPasswordToken;


@Service("userDetailService")
public class UserServiceImpl implements UserDetailsService, UserService {

	private final long PASSWORD_TOKEN_VALID_TIME = 24*60*60*1000;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		com.wkrzywiec.spring.library.entity.User user = userDAO.getActiveUser(username);
		
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
					authList);
	}
	
	@Override
	@Transactional
	public boolean isUsernameAlreadyInUse(String username){
		
		boolean userInDb = true;
		if (userDAO.getActiveUser(username) == null) userInDb = false;
		return userInDb;
	}
	
	@Override
	@Transactional
	public boolean isEmailAlreadyInUse(String email){
		
		boolean userInDb = true;
		if (userDAO.getActiveUserByEmail(email) == null) userInDb = false;
		return userInDb;
	}

	@Override
	@Transactional
	public User saveReaderUser(UserDTO user, String changedByUsername) {
		
		User userEntity = convertUserDTOtoUserEntity(user, Roles.USER.toString());
		userEntity = userDAO.saveUser(userEntity, changedByUsername);
		
		return userEntity;
	}
	
	@Override
	@Transactional
	public User saveSpecialUser(UserDTO user, String changedByUsername) {
		
		String roleName;
		User userEntity; 
		
		if (user.getRole().equals("ADMIN")){
			roleName = Roles.ADMIN.toString();
		} else {
			roleName = Roles.LIBRARIAN.toString();
		}
		
		userEntity = convertUserDTOtoUserEntity(user, roleName);
		
		userEntity = userDAO.saveUser(userEntity, changedByUsername);
		return userEntity;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
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
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public User enableUser(int id, String changedByUsername) {
		
		User user = this.getUserById(id);
		if (!user.isEnable()) {
			user = userDAO.enableUser(id, changedByUsername);
		}
		
		return user;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
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
	@Transactional
	public List<User> getAllUsers() {
		return userDAO.getAllUsers();
	}
	
	@Override
	@Transactional
	public User getUserById(int id) {
		return userDAO.getUserById(id);
	}
	
	

	@Override
	@Transactional
	public User getUserByUsername(String username) {
		
		return userDAO.getActiveUser(username);
	}
	
	@Override
	@Transactional
	public User getUserByEmail(String email) {
		
		return userDAO.getActiveUserByEmail(email);
	}

	@Override
	@Transactional
	public List<User> searchUsers(String searchText, int pageNo,
			int resultsPerPage) {
		
		List<User> userList = userDAO.searchUsers(searchText, pageNo, resultsPerPage);
		
		return userList;
	}

	@Override
	public int searchUserPagesCount(String searchText, int resultsPerPage) {
		
		int userCount = searchUsersResultsCount(searchText);
		int pageCount = (int) Math.floorDiv(userCount, resultsPerPage) + 1;
		
		return pageCount;
	}

	@Override
	@Transactional
	public int searchUsersResultsCount(String searchText) {
		
		int userCount = userDAO.searchUsersTotalCount(searchText);
		
		return userCount;
	}
	
	@Override
	@Transactional
	public List<UserLogDTO> getUserLogs(int id) {
		
		List<UserLog> userLogList;
		List<UserLogDTO> userLogDTOList;
		
		userLogList = userDAO.getUserLogs(id);
		userLogDTOList = this.convertUserLogsEntityListToUserLogsDTOList(userLogList);
		
		return userLogDTOList;
	}
	
	@Override
	@Transactional
	public boolean isUserAlreadyHasResetPasswordToken(String email) {
		
		boolean isUserHasPasswordToken = false;
		User user = null;
		UserPasswordToken userPasswordToken = null;
		user = this.getUserByEmail(email);
		
		if (user != null) {
			
			userPasswordToken = userDAO.getUserPasswordResetTokenByUserId(user.getId());
		}
		
		isUserHasPasswordToken =  userPasswordToken != null ? true : false;
		
		return isUserHasPasswordToken;
	}
	
	@Override
	@Transactional
	public UserPasswordToken createPasswordResetTokenForEmail(String email) {
		
		UserPasswordToken userPasswordToken = null;
		String token = UUID.randomUUID().toString();
		
		User user = userDAO.getActiveUserByEmail(email);
		
		userPasswordToken = new UserPasswordToken();
		userPasswordToken.setUser(user);
		userPasswordToken.setToken(token);
		userPasswordToken.setDueDate(new Timestamp(System.currentTimeMillis() + PASSWORD_TOKEN_VALID_TIME));
		userPasswordToken = userDAO.createPasswordResetToken(userPasswordToken);
		
		return userPasswordToken;
	}
	
	@Override
	@Transactional
	public UserPasswordToken updateResetPasswordTokenForEmail(String email) {
		
		User user = null;
		UserPasswordToken userPasswordToken = null;
		user = this.getUserByEmail(email);
		
		if (user != null) {
			
			userPasswordToken = userDAO.getUserPasswordResetTokenByUserId(user.getId());
			String token = UUID.randomUUID().toString();
			Timestamp dueDate = new Timestamp(System.currentTimeMillis() + PASSWORD_TOKEN_VALID_TIME);
			
			userPasswordToken.setToken(token);
			userPasswordToken.setDueDate(dueDate);
			userPasswordToken = userDAO.updateResetPasswordTokenForUser(userPasswordToken);
		}
		
		return userPasswordToken;
	}
	
	@Override
	@Transactional
	public void deleteUserPassword(int userId) {
		userDAO.deleteUserPassword(userId);
	}

	@Override
	@Transactional
	public boolean isUserTokenValid(int userId, String token) {
		
		boolean isUserTokenValid = false;
		UserPasswordToken userPasswordToken = null;
		userPasswordToken = userDAO.getUserPasswordResetTokenByUserId(userId);
		if (userPasswordToken != null) {
			Timestamp current = new Timestamp(System.currentTimeMillis());
			if (userPasswordToken.getDueDate().compareTo(current) > 0) {
				isUserTokenValid = userPasswordToken.getToken().equals(token) ? true : false;
			}
		}
		 
		return isUserTokenValid;
	}
	
	@Override
	@Transactional
	public void updateUserPassword(int userId, PasswordDTO passwordDTO) {
		
		User user = null;
		user = userDAO.getUserById(userId);
		
		user.setPassword(passwordEncoder.encode(passwordDTO.getPassword()));
		userDAO.updatePassword(user);
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
	
	private User convertUserDTOtoUserEntity(UserDTO user, String userRole) {
		
		User userEntity = new User();
		Role role;
		
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
		
		role = this.getRoleByName(userRole);
		userEntity.addRole(role);
		
		return userEntity;
	}
	
	private List<UserLogDTO> convertUserLogsEntityListToUserLogsDTOList(List<UserLog> userLogList) {
		
		List<UserLogDTO> userLogDTOList = new ArrayList<UserLogDTO>();
		UserLogDTO userLogDTO;
		if (userLogList != null) {
			for (UserLog userLog : userLogList) {
				userLogDTO = this.convertUserLogEntityToUserLogDTO(userLog);
				userLogDTOList.add(userLogDTO);
			}
		}
		return userLogDTOList;
	}

	private UserLogDTO convertUserLogEntityToUserLogDTO(UserLog userLog) {
		
		UserLogDTO userLogDTO = new UserLogDTO();
		userLogDTO.setId(userLog.getId());
		userLogDTO.setUsername(userLog.getUser().getUsername());
		userLogDTO.setDated(userLog.getDated());
		userLogDTO.setField(userLog.getField());
		userLogDTO.setFromValue(userLog.getFromValue());
		userLogDTO.setToValue(userLog.getToValue());
		userLogDTO.setMessage(userLog.getMessage());
		userLogDTO.setChangedByUsername(userLog.getChangedByUsername());
		
		return userLogDTO;
	}
}
