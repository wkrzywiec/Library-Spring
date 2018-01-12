package com.wkrzywiec.spring.library.test.database;


import static org.junit.Assert.assertFalse;
import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.wkrzywiec.spring.library.config.LibraryConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = LibraryConfig.class)
public class DatabaseTest {


	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Test
	public void checkDatabaseConnection(){
		
		Session session = null;
		
		try{
			try {
			    session = sessionFactory.getCurrentSession();
			} catch (HibernateException e) {
			    session = sessionFactory.openSession();
			}
			
			session.beginTransaction();
			
			session.doWork(new Work() {
				
				public void execute (Connection connection) throws SQLException {
					assertFalse(connection.isClosed());
				}
				
			});
		} finally {
			 if ( session != null ) {
		            session.close();
		        }
		        if (sessionFactory != null) {
		            sessionFactory.close();
		        }
		}
	}
}
