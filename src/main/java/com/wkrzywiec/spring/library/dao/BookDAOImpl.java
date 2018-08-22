package com.wkrzywiec.spring.library.dao;

import java.util.ArrayList;
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
import com.wkrzywiec.spring.library.entity.Borrowed;
import com.wkrzywiec.spring.library.entity.LibraryLog;
import com.wkrzywiec.spring.library.entity.BookPenalty;
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
	public void borrowBook(int bookId, int userId, int days) {
		
		StoredProcedureQuery query = entityManager
				.createStoredProcedureQuery("bookBorrow")
				.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter(3, Integer.class, ParameterMode.IN)
				.setParameter(1, bookId)
				.setParameter(2, userId)
				.setParameter(3, days);
		
		query.execute();
		
	}

	@Override
	public void returnBook(int bookId) {
		
		StoredProcedureQuery query = entityManager
				.createStoredProcedureQuery("bookReturn")
				.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
				.setParameter(1, bookId);
		
		query.execute();
		
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
		
		Borrowed borrowed = null;
		
		try {
			borrowed =  (Borrowed) entityManager.createQuery("from Borrowed b where b.book.id = :bookId")
					.setParameter("bookId", id)
					.getSingleResult();
		} catch (NoResultException e) {
			return false;
		}
		
		if (borrowed != null) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public int getReservedBooksTotalCountByUser(int userId) {
		
		int count = 0;
		try {
			count =  entityManager.createQuery("from Reserved r where r.user.id = :userId")
					.setParameter("userId", userId)
					.getResultList().size();
		} catch (NoResultException e) {
		}
		return count;
	}

	@Override
	public int getBorrowedBooksTotalCountByUser(int userId) {
		
		int count = 0;
		try {
			count =  entityManager.createQuery("from Borrowed b where b.user.id = :userId")
					.setParameter("userId", userId)
					.getResultList().size();
		} catch (NoResultException e) {
		}
		return count;
	}

	@Override
	public int getReservedBooksTotalCountByBook(int bookId) {
		
		int count = 0;
		try {
			count =  entityManager.createQuery("from Reserved r where r.book.id = :bookId")
					.setParameter("bookId", bookId)
					.getResultList().size();
		} catch (NoResultException e) {
		}
		return count;
	}

	@Override
	public int getBorrowedBooksTotalCountByBook(int bookId) {
		
		int count = 0;
		try {
			count =  entityManager.createQuery("from Borrowed b where b.book.id = :bookId")
					.setParameter("bookId", bookId)
					.getResultList().size();
		} catch (NoResultException e) {
		}
		return count;
	}

	@Override
	public int getReservedBooksTotalCount() {
		
		int count = 0;
		try {
			count =  entityManager.createQuery("from Reserved r")
					.getResultList().size();
		} catch (NoResultException e) {
		}
		return count;
	}

	@Override
	public int getBorrowedBooksTotalCount() {
		
		int count = 0;
		try {
			count =  entityManager.createQuery("from Borrowed b")
					.getResultList().size();
		} catch (NoResultException e) {
		}
		return count;
	}

	@Override
	public List<Reserved> getAllReservedBooksList() {
		
		List<Reserved> reservedList = null;
		try {
			reservedList =  entityManager.createQuery("from Reserved r")
					.getResultList();
		} catch (NoResultException e) {
			reservedList = new ArrayList<Reserved>();
		}
		return reservedList;
	}

	@Override
	public List<Borrowed> getAllBorrowedBooksList() {
		
		List<Borrowed> borrowedList = null;
		try {
			borrowedList =  entityManager.createQuery("from Borrowed b")
					.getResultList();
		} catch (NoResultException e) {
			borrowedList = new ArrayList<Borrowed>();
		}
		return borrowedList;
	}

	@Override
	public List<Reserved> getReservedBooksListByUserId(int userId) {
		
		List<Reserved> reservedList = null;
		try {
			reservedList =  entityManager.createQuery("from Reserved r where r.user.id = :userId")
					.setParameter("userId", userId)
					.getResultList();
		} catch (NoResultException e) {
		}
		return reservedList;
	}

	@Override
	public List<Borrowed> getBorrowedBooksListByUserId(int userId) {
		
		List<Borrowed> borrowedList = null;
		try {
			borrowedList =  entityManager.createQuery("from Borrowed b where b.user.id = :userId")
					.setParameter("userId", userId)
					.getResultList();
		} catch (NoResultException e) {
		}
		return borrowedList;
	}
	
	@Override
	public Borrowed getBorrowedBookByBookId(int bookId) {
		
		Borrowed borrowed = null;
		try {
			borrowed =  (Borrowed) entityManager.createQuery("from Borrowed b where b.book.id = :bookId")
					.setParameter("bookId", bookId)
					.getSingleResult();
		} catch (NoResultException e) {
		}
		return borrowed;
	}

	@Override
	public List<Reserved> getReservedBooksListByBookId(int bookId) {
		
		List<Reserved> reservedList = null;
		try {
			reservedList =  entityManager.createQuery("from Reserved r where r.book.id = :bookId")
					.setParameter("bookId", bookId)
					.getResultList();
		} catch (NoResultException e) {
		}
		return reservedList;
	}

	@Override
	public List<Borrowed> getBorrowedBooksListByBookId(int bookId) {
		
		List<Borrowed> borrowedList = null;
		try {
			borrowedList =  entityManager.createQuery("from Borrowed b where b.book.id = :bookId")
					.setParameter("bookId", bookId)
					.getResultList();
		} catch (NoResultException e) {
		}
		return borrowedList;
	}

	@Override
	public List<LibraryLog> getLibraryLogsByUser(int userId) {
		
		List<LibraryLog> libraryLogs = null;
		try {
			libraryLogs =  entityManager.createQuery("from LibraryLog l where l.user.id = :userId order by l.dated desc")
					.setParameter("userId", userId)
					.getResultList();
		} catch (NoResultException e) {
		}
		return libraryLogs;
	}

	@Override
	public List<LibraryLog> getLibraryLogsByBook(int bookId) {
		List<LibraryLog> libraryLogs = null;
		try {
			libraryLogs =  entityManager.createQuery("from LibraryLog l where l.book.id = :bookId order by l.dated desc")
					.setParameter("bookId", bookId)
					.getResultList();
		} catch (NoResultException e) {
		}
		return libraryLogs;
	}

	@Override
	public List<BookPenalty> getBookPenaltiesByUser(int userId) {
		
		List<BookPenalty> penalties = null;
		try {
			penalties = entityManager.createQuery("from BookPenalty o where o.user.id = :userId")
					.setParameter("userId", userId)
					.getResultList();
		} catch (NoResultException e) {
		}
		return penalties;
	}

	@Override
	public BookPenalty getPenaltyForBook(int bookId) {
		
		BookPenalty penalty = null;
		
		try {
			penalty = (BookPenalty) entityManager.createQuery("from BookPenalty o where o.book.id = :bookId")
					.setParameter("bookId", bookId)
					.getSingleResult();
		} catch (NoResultException e) {
		}
		return penalty;
	}

	@Override
	public void setReturnDateForPenalty(int bookId) {
		
		StoredProcedureQuery query = entityManager
				.createStoredProcedureQuery("setPenaltyReturnDate")
				.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
				.setParameter(1, bookId);
		
		query.execute();
		
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
