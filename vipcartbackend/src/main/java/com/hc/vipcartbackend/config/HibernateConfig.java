package com.hc.vipcartbackend.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan("com.hc.vipcartbackend.dto")
@EnableTransactionManagement
public class HibernateConfig {

	/*private static final String DATABASE_URL = "jdbc:h2:tcp://localhost/~/vipcart";
	private static final String DATABASE_DRIVER = "org.h2.Driver";
	private static final String DATABASE_DIALECT = "org.hibernate.dialect.H2Dialect";
	private static final String DATABASE_USERNAME = "vip";
	private static final String DATABASE_PASSWORD = "";*/
	
	private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/oms";
	private static final String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DATABASE_DIALECT = "org.hibernate.dialect.MySQL8Dialect";
	private static final String DATABASE_USERNAME = "root";
	private static final String DATABASE_PASSWORD = "root";
	
	@Bean("dataSource")
	public DataSource getDataSource() {
		BasicDataSource datasource = new BasicDataSource();
		datasource.setDriverClassName(DATABASE_DRIVER);
		datasource.setUrl(DATABASE_URL);
		datasource.setUsername(DATABASE_USERNAME);
		datasource.setPassword(DATABASE_PASSWORD);
		return datasource;
	}
	
	@Bean
	public SessionFactory getSessionFactory(DataSource datasource) {
		LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(datasource);
		builder.addProperties(getHibenateProperties());
		builder.scanPackages("com.hc.vipcartbackend.dto");
		return builder.buildSessionFactory();
	}

	private Properties getHibenateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", DATABASE_DIALECT);
		properties.put("hibernate.show_sql", "true");
		properties.put("hibernate.format_sql", "true");
		return properties;
	}
	
	// transactionmanager bean
	@Bean
	public HibernateTransactionManager getTransctionManager(SessionFactory sessionfactory) {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionfactory);
		return transactionManager;
	}
}
