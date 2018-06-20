package com.wkrzywiec.spring.library.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Repository;

import com.wkrzywiec.spring.library.entity.Author;
import com.wkrzywiec.spring.library.entity.Book;
import com.wkrzywiec.spring.library.entity.BookCategory;

@Repository
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
public class BookDAOImpl implements BookDAO {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Author getAuthorByName(String authorName) {
		
		Author author = null;
		
		try {
			author = (Author) entityManager.createQuery("from Author a where a.name = :name")
					.setParameter("name", authorName)
					.getSingleResult();
		} catch (NoResultException nre) {
			
		}
		
		
		return author;
	}

	@Override
	public BookCategory getBookCategoryByName(String categoryName) {
		
		BookCategory category = null;
		
		try {
			category = (BookCategory) entityManager.createQuery("from BookCategory c where c.name = :category")
					.setParameter("category", categoryName)
					.getSingleResult();
		} catch (NoResultException nre) {
			
		}
		
		
		return category;
	}



	@Override
	public Book saveBook(Book book, String changedByUsername) {
		
		entityManager.persist(book);
		
		return null;
	}

	
}
