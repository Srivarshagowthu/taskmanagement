package com.ninjacart.task_mgmt_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.beans.factory.annotation.Value;

import javax.sql.DataSource;
import java.util.Properties;

@SpringBootApplication
@ComponentScan(basePackages = "com.ninjacart.task_mgmt_service")
public class TaskMgmtServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskMgmtServiceApplication.class, args);
	}

	// Define DataSource bean
	@Bean
	public DataSource dataSource(@Value("${spring.datasource.url}") String url,
								 @Value("${spring.datasource.username}") String username,
								 @Value("${spring.datasource.password}") String password,
								 @Value("${spring.datasource.driver-class-name}") String driverClassName) {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		return dataSource;
	}

	// Define SessionFactory bean
	@Bean(name = "sessionFactory")
	public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
		sessionFactoryBean.setDataSource(dataSource);

		// Optionally add entity packages to scan
		sessionFactoryBean.setPackagesToScan("com.ninjacart.task_mgmt_service.entity");

		// Hibernate properties
		Properties hibernateProperties = new Properties();
		hibernateProperties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect"); // Change to your DB dialect
		hibernateProperties.put("hibernate.hbm2ddl.auto", "update");
		hibernateProperties.put("hibernate.show_sql", "true");
		hibernateProperties.put("hibernate.format_sql", "true");

		sessionFactoryBean.setHibernateProperties(hibernateProperties);
		return sessionFactoryBean;
	}

	// Define Hibernate JpaVendorAdapter bean (optional)
	@Bean
	public HibernateJpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setGenerateDdl(true); // Automatically create DDL based on entity classes
		adapter.setShowSql(true); // Log SQL queries to console
		adapter.setDatabasePlatform("org.hibernate.dialect.H2Dialect"); // Change to your DB dialect
		return adapter;
	}
}