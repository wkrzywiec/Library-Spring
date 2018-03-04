package com.wkrzywiec.spring.library.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.jboss.logging.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wkrzywiec.spring.library.dao.UserDAO;
import com.wkrzywiec.spring.library.dto.UserDTO;
import com.wkrzywiec.spring.library.entity.Role;
import com.wkrzywiec.spring.library.entity.UserDetail;


@Service("userDetailService")
public class LibraryUserDetailService implements UserDetailsService, UserService {

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private Logger userLogger = LogManager.getLogger("userLoggerDB");
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		com.wkrzywiec.spring.library.entity.User user = userDAO.getActiveUser(username);
		
		boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
		
        Collection<? extends GrantedAuthority> authList = getUserAuthorities(user.getRoles());
        
		return new User(
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
	public void saveReaderUser(UserDTO user) {
		com.wkrzywiec.spring.library.entity.User userEntity = convertUserDTOtoUserEntity(user);
		userDAO.saveUser(userEntity);
		
		ThreadContext.put("username", user.getUsername());
		ThreadContext.put("field", "ALL");
		ThreadContext.put("from_value", "");
		ThreadContext.put("to_value", user.toString());
		userLogger.info("New user");
		ThreadContext.clearAll();
	}
	
	@Override
	public Role getRoleByName(String roleName) {
		return userDAO.getRoleByName(roleName);
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
	
	private com.wkrzywiec.spring.library.entity.User convertUserDTOtoUserEntity(UserDTO user) {
		
		com.wkrzywiec.spring.library.entity.User userEntity = new com.wkrzywiec.spring.library.entity.User();
		
		userEntity.setUsername(user.getUsername());
		userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
		userEntity.setEmail(user.getEmail());
		userEntity.setEnable(true);
		
		UserDetail userDetail = new UserDetail();
		userDetail.setFirstName(user.getFirstName());
		userDetail.setLastName(user.getLastName());
		userDetail.setPhone(user.getPhone());
		userDetail.setBirthday(user.getBirthday());
		userDetail.setAddress(user.getAddress());
		userDetail.setPostalCode(user.getPostalCode());
		userDetail.setCity(user.getCity());
		
		userEntity.setUserDetail(userDetail);
		
		return userEntity;
	}
}
