package com.yawintutor;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "secondaryEntityManagerFactory", transactionManagerRef = "secondaryTransactionManager", basePackages = {
		"com.yawintutor.secondary" })
public class SecondaryDBConfig {
	@Bean(name = "secondaryDataSource")
	@ConfigurationProperties(prefix = "spring.seconddatasource")
	public DataSource secondaryDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "secondaryEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean secondaryEntityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("secondaryDataSource") DataSource secondaryDataSource) {
		return builder.dataSource(secondaryDataSource).packages("com.yawintutor.secondary").build();
	}

	@Bean(name = "secondaryTransactionManager")
	public PlatformTransactionManager secondaryTransactionManager(
			@Qualifier("secondaryEntityManagerFactory") EntityManagerFactory secondaryEntityManagerFactory) {
		return new JpaTransactionManager(secondaryEntityManagerFactory);
	}
}