package com.wkrzywiec.spring.library.dao;

import com.wkrzywiec.spring.library.entity.Customer;

public interface UserDAO {

	public Customer getCustomer(int id);
}
