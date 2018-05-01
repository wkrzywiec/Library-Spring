package com.wkrzywiec.spring.library.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

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
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		com.wkrzywiec.spring.library.entity.User user = userDAO.getActiveUser(username);
		System.out.println(user.getRoles());
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
	}
	
	@Override
	public Role getRoleByName(String roleName) {
		return userDAO.getRoleByName(roleName);
	}
	
	
	@Override
	public List<com.wkrzywiec.spring.library.entity.User> getAllUsers() {
		return userDAO.getAllUsers();
	}

	private Collection<? extends GrantedAuthority> getUserAuthorities(Set<Role> modelAuthSet) {
		
		List<Role> modelAuthList = convertRolesSetToList(modelAuthSet);
		
        List<GrantedAuthority> authList = getGrantedUserAuthority(modelAuthList);
        return authList;
    }
	
	

	@Override
	public List<com.wkrzywiec.spring.library.entity.User> searchUsers(String searchText, int pageNo,
			int resultsPerPage) {
		
		List<com.wkrzywiec.spring.library.entity.User> userList = userDAO.searchUsers(searchText, pageNo, resultsPerPage);
		
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
