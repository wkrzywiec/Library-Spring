package com.wkrzywiec.spring.library.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
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
	@Transactional
	public void saveUser(User user, String roleName) {
		
		Role role;
		
		role = getRoleByName(roleName);
		user.addRole(role);
		
		if (roleName != Roles.USER.toString()){
			role = getRoleByName(Roles.USER.toString());
			user.addRole(role);
		}
		
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

	@Override
	@Transactional
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
}
