package com.wkrzywiec.spring.library.dao;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.wkrzywiec.spring.library.entity.User;


@Repository
public class UserDAOImpl implements UserDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public User getActiveUser(String username) {
		
		List<User> userList = new ArrayList<User>();
		
		Query<User> query = getCurrentSession().createQuery("from User u where u.login = :login");
		query.setParameter("login", username);
		
		userList = query.list();
		
		if (userList.size() > 0 ){
			return userList.get(0);
		} else 
			return null;
	}
	
	protected Session getCurrentSession(){
		return sessionFactory.getCurrentSession();
	}
}
