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
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.ns.dualds.repository.DB_One", entityManagerFactoryRef = "dbOneEntityManager", transactionManagerRef = "dbOneTransactionManager")
public class DbOneConfiguration {

	@Autowired
	Environment env;

	@Bean(name = "dbOneDataSource")
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource dbOneDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "dbOneEntityManager")
	@Primary
	public LocalContainerEntityManagerFactoryBean dbOneEntityManager(EntityManagerFactoryBuilder builder) {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.datasource.hibernate.ddl-auto"));
		properties.put("hibernate.dialect", env.getProperty("spring.datasource.hibernate.dialect"));

		return builder.dataSource(dbOneDataSource()) //
				.packages("com.ns.dualds.model.DB_One") //
				.persistenceUnit("dbOne") //
				.properties(properties) //
				.build();
	}

	@Bean(name = "dbOneTransactionManager")
	@Primary
	public PlatformTransactionManager dbOneTransactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setDataSource(dbOneDataSource());
		transactionManager.setPersistenceUnitName("dbOne");
		return transactionManager;
	}

}
