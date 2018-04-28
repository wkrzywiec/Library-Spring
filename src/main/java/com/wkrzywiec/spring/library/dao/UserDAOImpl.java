package com.wkrzywiec.spring.library.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.search.FullTextQuery;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;

import com.wkrzywiec.spring.library.entity.Role;
import com.wkrzywiec.spring.library.entity.Roles;
import com.wkrzywiec.spring.library.entity.User;


@Repository
public class UserDAOImpl implements UserDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
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
	@Transactional
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
	@Transactional
	public void saveUser(User user) {
		
		Role userRole = getRoleByName(Roles.USER.toString());
		user.addRole(userRole);
		
		entityManager.persist(user);
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
	@Transactional
	public List<User> getAllUsers() {
		
		List<User> list = null;
		list = entityManager.createQuery("from User u")
				.getResultList();
		
		return list;
	}
}
