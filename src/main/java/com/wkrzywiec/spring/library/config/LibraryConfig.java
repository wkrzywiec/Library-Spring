package com.wkrzywiec.spring.library.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;


@Configuration
@EnableWebMvc
@EnableTransactionManagement
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan(basePackages="com.wkrzywiec.spring.library")
@PropertySource(value = { "classpath:properties/datasource.properties", "classpath:properties/hibernate.properties" })
public class LibraryConfig implements WebMvcConfigurer {

	@Autowired
    private Environment env;
	
	@Value("${hibernate.hbm2ddl.auto}")
	private String hibernatehbm2ddl;
	
	@Value("${hibernate.dialect}")
	private String hibernateDialect;
	
	@Value("${hibernate.search.default.directory_provider}")
	private String hibernateDirectoryProvider;
	
	@Value("${hibernate.search.default.indexBase}")
	private String hibernateIndexBase;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	public ViewResolver getViewResolver(){
		
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
	
	@Bean
	public DataSource getDataSource(){
	
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		
		dataSource.setDriverClassName(env.getProperty("datasource.driver"));
		dataSource.setUrl(env.getProperty("datasource.url"));
		dataSource.setUsername(env.getProperty("datasource.user"));
		dataSource.setPassword(env.getProperty("datasource.password"));
		return dataSource;
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(){
		LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
		entityManager.setDataSource(getDataSource());
		entityManager.setPackagesToScan("com.wkrzywiec.spring.library.entity");
 
      JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
      entityManager.setJpaVendorAdapter(vendorAdapter);
      entityManager.setJpaProperties(additionalProperties());
 
      return entityManager;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager(){
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactoryBean().getObject());
		 
		return transactionManager; 
	 }
	
	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
		return new PersistenceExceptionTranslationPostProcessor();
	   }
	
	private Properties additionalProperties() {
		
		
		Properties properties = new Properties();
	    properties.setProperty("hibernate.hbm2ddl.auto", hibernatehbm2ddl);
	    properties.setProperty("hibernate.dialect", hibernateDialect);
	    properties.setProperty("hibernate.search.default.directory_provider", hibernateDirectoryProvider);
	    properties.setProperty("hibernate.search.default.indexBase", hibernateIndexBase);
	   
	    return properties;
	}
	
	@Bean
	public JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	    mailSender.setHost("smtp.gmail.com");
	    mailSender.setPort(587);
	     
	    mailSender.setUsername("YOUR_EMAIL_ADDRESS");
	    mailSender.setPassword("YOUR_PASSWORD");
	     
	    Properties props = mailSender.getJavaMailProperties();
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.debug", "true");
	     
	    return mailSender;
	}
	
}
