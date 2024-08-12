package com.ns.dualds.config.persistance;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.ns.dualds.repository.DB_Two", entityManagerFactoryRef = "dbTwoEntityManager", transactionManagerRef = "dbTwoTransactionManager")
public class DbTwoConfiguration {

	@Autowired
	Environment env;

	@Bean(name = "dbTwoDataSource")
	@ConfigurationProperties(prefix = "spring.datasource-two")
	public DataSource dbTwoDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "dbTwoEntityManager")
	public LocalContainerEntityManagerFactoryBean dbOneEntityManager(EntityManagerFactoryBuilder builder) {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.datasource-two.hibernate.ddl-auto"));
		properties.put("hibernate.dialect", env.getProperty("spring.datasource-two.hibernate.dialect"));

		return builder.dataSource(dbTwoDataSource()) //
				.packages("com.ns.dualds.model.DB_Two") //
				.persistenceUnit("dbTwo") //
				.properties(properties) //
				.build();
	}

	@Bean(name = "dbTwoTransactionManager")
	public PlatformTransactionManager dbOneTransactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setDataSource(dbTwoDataSource());
		transactionManager.setPersistenceUnitName("dbTwo");
		return transactionManager;
	}

}
