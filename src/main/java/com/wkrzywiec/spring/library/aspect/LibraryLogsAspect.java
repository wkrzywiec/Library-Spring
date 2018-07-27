package com.wkrzywiec.spring.library.aspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wkrzywiec.spring.library.service.UserService;

@Aspect
@Component
public class LibraryLogsAspect {

	@Autowired
	UserService userService;
	
	private Logger libraryLogger = LogManager.getLogger("libraryLoggerDB");
	
	@Pointcut("execution(* com.wkrzywiec.spring.library.service.LibraryServiceImpl.reserveBook(..))")
	public void reserveBook() {}
	
	@Pointcut("execution(* com.wkrzywiec.spring.library.service.LibraryServiceImpl.borrowBook(..))")
	public void borrowBook() {}
	
	@Pointcut("execution(* com.wkrzywiec.spring.library.service.LibraryServiceImpl.returnBook(..))")
	public void returnBook() {}
	
	@AfterReturning("reserveBook()")
	public void reserveBook(JoinPoint joinPoint) {
		this.getLogsDetails(joinPoint);
		libraryLogger.info("Book Reserved");
		this.clearAllLogsDetails();
	}
	
	@AfterReturning("borrowBook()")
	public void borrowBook(JoinPoint joinPoint) {
		this.getLogsDetails(joinPoint);
		libraryLogger.info("Book Borrowed");
		this.clearAllLogsDetails();
	}
	
	@AfterReturning("returnBook()")
	public void returnBook(JoinPoint joinPoint) {
		this.getLogsDetails(joinPoint);
		libraryLogger.info("Book Returned");
		this.clearAllLogsDetails();
	}
	
	private void getLogsDetails(JoinPoint joinPoint) {
		Object[] lArgs = joinPoint.getArgs();
		int bookId = (int) lArgs[0];
		int userId = (int) lArgs[1];
		
		String username = userService.getUserById(userId).getUsername();
		ThreadContext.put("book_id", Integer.toString(bookId));
		ThreadContext.put("user_id", Integer.toString(userId));
		ThreadContext.put("changed_by_username", username);
	}
	
	private void clearAllLogsDetails() {
		ThreadContext.clearAll();
	}
}
