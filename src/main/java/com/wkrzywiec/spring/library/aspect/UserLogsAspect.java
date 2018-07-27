package com.wkrzywiec.spring.library.aspect;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wkrzywiec.spring.library.entity.User;
import com.wkrzywiec.spring.library.service.UserService;

@Aspect
@Component
public class UserLogsAspect {
	
	@Autowired
	UserService userService;
	
	private Logger userLogger = LogManager.getLogger("userLoggerDB");
	
	
	@Pointcut("execution(* com.wkrzywiec.spring.library.dao.UserDAOImpl.saveUser(..))")
	public void saveUser() {}
	
	@Pointcut("execution(* com.wkrzywiec.spring.library.dao.UserDAOImpl.enableUser(..))")
	public void enableUser() {}
	
	@Pointcut("execution(* com.wkrzywiec.spring.library.dao.UserDAOImpl.disableUser(..))")
	public void disableUser() {}
	
	@Pointcut("execution(* com.wkrzywiec.spring.library.dao.UserDAOImpl.updateUser(..))")
	public void updateUser() {}
	
	@AfterReturning(pointcut="saveUser()",
					returning="userObject")
	public void saveNewUser(JoinPoint joinPoint, Object userObject){
		
		Object[] lArgs = joinPoint.getArgs();
		User user = (User) lArgs[0];
		User savedUser = (User) userObject;
		
		ThreadContext.put("user_id", Integer.toString(savedUser.getId()));
		ThreadContext.put("field", "ALL");
		ThreadContext.put("from_value", "");
		ThreadContext.put("to_value", user.toString());
		ThreadContext.put("changed_by_username", user.getUsername());
		userLogger.info("New user");
		ThreadContext.clearAll();
	}
	
	@AfterReturning("enableUser()")
	public void enableUserImpl(JoinPoint joinPoint) {
		
		Object[] lArgs = joinPoint.getArgs();
		int id = (int) lArgs[0];
		String changeByUsername = (String) lArgs[1];
		
		ThreadContext.put("user_id", Integer.toString(id));
		ThreadContext.put("field", "Enable");
		ThreadContext.put("from_value", "FALSE");
		ThreadContext.put("to_value", "TRUE");
		ThreadContext.put("changed_by_username", changeByUsername);
		userLogger.info("User was enabled");
		ThreadContext.clearAll();
	}
	
	@AfterReturning("disableUser()")
	public void disableUserImpl(JoinPoint joinPoint) {
		
		Object[] lArgs = joinPoint.getArgs();
		int id = (int) lArgs[0];
		String changeByUsername = (String) lArgs[1];
		
		ThreadContext.put("user_id", Integer.toString(id));
		ThreadContext.put("field", "Enable");
		ThreadContext.put("from_value", "TRUE");
		ThreadContext.put("to_value", "FALSE");
		ThreadContext.put("changed_by_username", changeByUsername);
		userLogger.info("User was disabled");
		ThreadContext.clearAll();
	}
	
	@Before("updateUser()")
	public void updateUserImpl(JoinPoint joinPoint) {
		
		Object[] lArgs = joinPoint.getArgs();
		int id = (int) lArgs[0];
		Map<String, String> changedFields = (Map<String, String>) lArgs[1];
		String changedByUsername = (String) lArgs[2];
		
		User user = userService.getUserById(id);
		
		for (Map.Entry<String, String> entry : changedFields .entrySet()) {
			
			ThreadContext.put("user_id", String.valueOf(id));
			ThreadContext.put("field", entry.getKey());
			ThreadContext.put("to_value", entry.getValue());
			ThreadContext.put("changed_by_username", changedByUsername);
			
			if (entry.getKey().equals("email")) {
				ThreadContext.put("from_value", user.getEmail());
				userLogger.info("Email was changed");
			}
			if (entry.getKey().equals("firstName")) {
				ThreadContext.put("from_value", user.getFirstName());
				userLogger.info("First Name was changed");
			}
			if (entry.getKey().equals("lastName")) {
				ThreadContext.put("from_value", user.getLastName());
				userLogger.info("Last Name was changed");
			}
			if (entry.getKey().equals("phone")) {
				ThreadContext.put("from_value", user.getPhone());
				userLogger.info("Phone was changed");
			}
			if (entry.getKey().equals("address")) {
				ThreadContext.put("from_value", user.getAddress());
				userLogger.info("Address was changed");
			}
			if (entry.getKey().equals("postalCode")) {
				ThreadContext.put("from_value", user.getPostalCode());
				userLogger.info("Postal Code was changed");
			}
			if (entry.getKey().equals("city")) {
				ThreadContext.put("from_value", user.getCity());
				userLogger.info("City was changed");
			}
			ThreadContext.clearAll();
	    }		
	}
}
