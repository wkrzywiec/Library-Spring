package com.wkrzywiec.spring.library.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.wkrzywiec.spring.library.entity.Author;
import com.wkrzywiec.spring.library.entity.Book;
import com.wkrzywiec.spring.library.entity.BookCategory;
import com.wkrzywiec.spring.library.entity.Reserved;

@Repository
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
public class BookDAOImpl implements BookDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Book getBookByGoogleId(String googleId) {
		
		Book book = null;
		
		try {
			book = (Book) entityManager.createQuery("from Book a where a.googleId = :googleId")
					.setParameter("googleId", googleId)
					.getSingleResult();
		} catch (NoResultException nre) {
			
		}
		
		return book;
	}

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
	public List<Book> searchBookList(String searchText, int pageNo, int resultsPerPage) {
		
		FullTextQuery jpaQuery = this.searchBooksQuery(searchText);
		
		jpaQuery.setMaxResults(resultsPerPage);
		jpaQuery.setFirstResult((pageNo-1) * resultsPerPage);
		
		List<Book> bookList = jpaQuery.getResultList();
		
		return bookList;
	}

	@Override
	@Transactional
	public int searchBookResultsCount(String searchText) {
		
		FullTextQuery jpaQuery = this.searchBooksQuery(searchText);
		
		int booksCount = jpaQuery.getResultSize();
		return booksCount;
	}

	@Override
	public Book getBookById(int id) {

		Book book = null;
		
		try {
			book = (Book) entityManager.createQuery("from Book b where b.id = :id")
					.setParameter("id", id)
					.getSingleResult();
		} catch (NoResultException e) {
		}
		
		return book;
	}

	@Override
	public Book saveBook(Book book, String changedByUsername) {
		
		entityManager.persist(book);
		
		return null;
	}
	
	@Override
	public Book reserveBook(int id, int userId, int days) {
		
		Book book = null;
		
		StoredProcedureQuery query = entityManager
				.createStoredProcedureQuery("bookReserve")
				.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter(3, Integer.class, ParameterMode.IN)
				.setParameter(1, id)
				.setParameter(2, userId)
				.setParameter(3, days);
		
		query.execute();
		
		book = this.getBookById(id);
		
		return book;
	}
	
	

	@Override
	public boolean isBookReserved(int id) {
		
		Reserved reserved = null;
		try {
			reserved =  (Reserved) entityManager.createQuery("from Reserved r where r.book.id = :bookId")
					.setParameter("bookId", id)
					.getSingleResult();
		} catch (NoResultException e) {
			return false;
		}
	
		if (reserved != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean isBookBorrowed(int id) {
		
		Object object = null;
		
		try {
			object =  entityManager.createQuery("from Borrowed b where b.id = :id")
					.setParameter("id", id)
					.getSingleResult();
		} catch (NoResultException e) {
			return false;
		}
		
		if (object != null) {
			return true;
		} else {
			return false;
		}
	}

	private FullTextQuery searchBooksQuery (String searchText) {
		
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
		QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory()
				.buildQueryBuilder()
				.forEntity(Book.class)
				.get();
				
		org.apache.lucene.search.Query luceneQuery = queryBuilder
			.keyword()
			.wildcard()
			.onFields("title", "authors.name")
				.boostedTo(5f)
			.andField("description")
			.andField("categories.name")
			.andField("publisher")
			.matching(searchText + "*")
			.createQuery();
		
		FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, Book.class);

		return jpaQuery;
	}

	
}
