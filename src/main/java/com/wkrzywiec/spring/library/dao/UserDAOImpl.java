package com.wkrzywiec.spring.library.dao;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.wkrzywiec.spring.library.entity.Customer;

@Repository
public class UserDAOImpl implements UserDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public Customer getCustomer(int id) {
		return getCurrentSession().get(Customer.class, id);
	}
	
	protected Session getCurrentSession(){
		return sessionFactory.getCurrentSession();
	}
}
