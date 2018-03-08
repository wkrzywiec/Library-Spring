package com.wkrzywiec.spring.library.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.wkrzywiec.spring.library.entity.Role;
import com.wkrzywiec.spring.library.entity.Roles;
import com.wkrzywiec.spring.library.entity.User;


@Repository
public class UserDAOImpl implements UserDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public User getActiveUser(String username) {
		
		Query<User> query = getCurrentSession().createQuery("from User u where u.username = :username");
		query.setParameter("username", username);
		
		try {
			User user = query.getSingleResult();
			return user;
		} catch(NoResultException e) {
			return null;
		}
		
	}
	
	@Override
	@Transactional
	public User getActiveUserByEmail(String email) {
		
		Query<User> query = getCurrentSession().createQuery("from User u where u.email = :email");
		query.setParameter("email", email);
		
		try {
			User user = query.getSingleResult();
			return user;
		} catch(NoResultException e) {
			return null;
		}
	}

	@Override
	@Transactional
	public void saveUser(User user) {
		
		Role userRole = getRoleByName(Roles.USER.toString());
		user.addRole(userRole);
		
		getCurrentSession().persist(user);
	}

	@Override
	@Transactional
	public Role getRoleByName(String roleName) {
		
		Query<Role> query = getCurrentSession().createQuery("from Role r where r.name = :name");
		query.setParameter("name", roleName);

		return query.getSingleResult();
		
	}
	
	@Override
	@Transactional
	public List<User> getAllUsers() {
		
		Query<User> query = getCurrentSession().createQuery("from User u");
		List<User> userList = query.getResultList();
		
		return userList;
	}

	protected Session getCurrentSession(){
		return sessionFactory.getCurrentSession();
	}
}
