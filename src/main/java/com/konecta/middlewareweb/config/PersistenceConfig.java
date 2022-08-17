package com.konecta.middlewareweb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.konecta.middlewareweb.negocio.util.AuditingDateTimeProvider;
import com.konecta.middlewareweb.negocio.util.UsernameAuditorAware;


@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "com.konecta.middlewareweb.model")
@EnableJpaRepositories("com.konecta.middlewareweb.repositories")
@EnableJpaAuditing(auditorAwareRef = "auditingProvider" , dateTimeProviderRef = "dateTimeProvider")
public class PersistenceConfig {

	
	@Bean
	public UsernameAuditorAware auditingProvider() {
		return new UsernameAuditorAware();
	}

	@Bean
	public AuditingDateTimeProvider dateTimeProvider() {
		return new AuditingDateTimeProvider();
	}
}
