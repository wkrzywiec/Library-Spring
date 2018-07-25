package com.wkrzywiec.spring.library.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wkrzywiec.spring.library.entity.Role;
import com.wkrzywiec.spring.library.entity.Roles;
import com.wkrzywiec.spring.library.entity.User;
import com.wkrzywiec.spring.library.entity.UserLog;


@Repository
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
public class UserDAOImpl implements UserDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public User getActiveUser(String username) {
		
		User user;
		
		try {
			user = (User) entityManager.createQuery("from User u where u.username = :username")
					.setParameter("username", username)
					.getSingleResult();
		} catch (NoResultException e) {
			user = null;
		}
		
		return user;
	}
	
	@Override
	public User getActiveUserByEmail(String email) {
		
		User user;
		
		try {
			user = (User) entityManager.createQuery("from User u where u.email = :email")
					.setParameter("email", email)
					.getSingleResult();
		} catch (NoResultException e) {
			user = null;
		}
		
		return user;
	}
	
	@Override
	public User getUserById(int id) {
		
		User user;
		
		try {
			user = (User) entityManager.createQuery("from User u where u.id = :id")
					.setParameter("id", id)
					.getSingleResult();
		} catch (NoResultException e) {
			user = null;
		}
		
		return user;
	}

	@Override
	public User saveUser(User user, String changedByUsername) {
		
		entityManager.persist(user);
		
		return user;
	}
	
	@Override
	public User updateUser(int id, Map<String, String> changedFields, String changedByUsername) {
		
		User user = entityManager.find(User.class, id);
		
		if (changedFields.containsKey("email"))		this.updateUserEmail(user, changedFields.get("email"), changedByUsername);
		if (changedFields.containsKey("firstName"))		this.updateUserFirstName(user, changedFields.get("firstName"), changedByUsername);
		if (changedFields.containsKey("lastName"))		this.updateUserLastName(user, changedFields.get("lastName"), changedByUsername);
		if (changedFields.containsKey("phone"))		this.updateUserPhone(user, changedFields.get("phone"), changedByUsername);
		if (changedFields.containsKey("address"))		this.updateUserAddress(user, changedFields.get("address"), changedByUsername);
		if (changedFields.containsKey("postalCode"))		this.updateUserPostalCode(user, changedFields.get("postalCode"), changedByUsername);
		if (changedFields.containsKey("city"))		this.updateUserCity(user, changedFields.get("city"), changedByUsername);
	
		return user;
	}
	
	@Override
	public User enableUser(int id, String changedByUsername) {
		
		User user = entityManager.find(User.class, id);
		
		user.setEnable(true);
		entityManager.persist(user);
		return user;
	}

	@Override
	public User disableUser(int id, String changedByUsername) {
		
		User user = entityManager.find(User.class, id);
		
		user.setEnable(false);
		entityManager.persist(user);
		
		return user;
	}

	@Override
	@Transactional
	public Role getRoleByName(String roleName) {
	
		Role role;
		try {
			role = (Role) entityManager.createQuery("from Role r where r.name = :name")
					.setParameter("name", roleName)
					.getSingleResult();
		} catch (NoResultException e) {
			role = null;
		}
		return role;
	}
	
	@Override
	public List<User> getAllUsers() {
		
		List<User> list = null;
		list = entityManager.createQuery("from User u")
				.getResultList();
		
		return list;
	}

	@Override
	public List<User> searchUsers(String searchText, int pageNo, int resultsPerPage) {
		
		FullTextQuery jpaQuery = searchUsersQuery(searchText);
		
		jpaQuery.setMaxResults(resultsPerPage);
		jpaQuery.setFirstResult((pageNo-1) * resultsPerPage);
		
		List<User> userList = jpaQuery.getResultList();
		
		return userList;
	}

	@Override
	@Transactional
	public int searchUsersTotalCount(String searchText) {
		
		FullTextQuery jpaQuery = searchUsersQuery(searchText);
		
		int usersCount = jpaQuery.getResultSize();
		
		return usersCount;
	}
	
	@Override
	public List<UserLog> getUserLogs(int id) {
		
		List<UserLog> userLogList;
		
		userLogList = entityManager.createQuery("from UserLog u where u.user.id = :userId")
					.setParameter("userId", id)
					.getResultList();
		
		return userLogList;
	}

	private FullTextQuery searchUsersQuery (String searchText) {
		
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
		QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory()
				.buildQueryBuilder()
				.forEntity(User.class)
				.get();
				
		org.apache.lucene.search.Query luceneQuery = queryBuilder
			.keyword()
			.wildcard()
			.onFields("username", "email", "lastName")
				.boostedTo(5f)
			.andField("firstName")
			.matching(searchText + "*")
			.createQuery();
		
		FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, User.class);

		return jpaQuery;
	}
	
	private void updateUserEmail(User user, String email, String changedByUsername) {		
		
		user.setEmail(email);
		entityManager.merge(user);
	}

	private void updateUserFirstName(User user, String firstName, String changedByUsername) {
		
		user.setFirstName(firstName);
		entityManager.merge(user);
	}

	private void updateUserLastName(User user, String lastName, String changedByUsername) {
		
		user.setLastName(lastName);
		entityManager.merge(user);
	}
	
	private void updateUserPhone(User user, String phone, String changedByUsername) {
		
		user.setPhone(phone);
		entityManager.merge(user);
	}

	private void updateUserAddress(User user, String address, String changedByUsername) {
		
		user.setAddress(address);
		entityManager.merge(user);
	}

	private void updateUserPostalCode(User user, String postalCode, String changedByUsername) {
		
		user.setPostalCode(postalCode);
		entityManager.merge(user);
	}

	private void updateUserCity(User user, String city, String changedByUsername) {
		
		user.setCity(city);
		entityManager.merge(user);
	}

}
