package com.wkrzywiec.spring.library.aspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.wkrzywiec.spring.library.dto.UserDTO;

@Aspect
@Component
public class UserLoggingAspect {

	private Logger userLogger = LogManager.getLogger("userLoggerDB");
	
	@Pointcut("execution(* com.wkrzywiec.spring.library.service.LibraryUserDetailService.saveReaderUser(..))")
	public void saveReader() {}
	
	@AfterReturning("saveReader()")
	public void saveNewReader(JoinPoint joinPoint){
		
		Object[] lArgs = joinPoint.getArgs();
		UserDTO user = (UserDTO) lArgs[0];
		
		ThreadContext.put("username", user.getUsername());
		ThreadContext.put("field", "ALL");
		ThreadContext.put("from_value", "");
		ThreadContext.put("to_value", user.toString());
		userLogger.info("New user");
		ThreadContext.clearAll();
	}
	
}
