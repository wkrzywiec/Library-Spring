package com.wkrzywiec.spring.library.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class LibraryMvcServletDispatcherInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {LibraryConfig.class};
	}

	@Override
	protected String[] getServletMappings() {
		return new String [] {"/"};
	}

}
