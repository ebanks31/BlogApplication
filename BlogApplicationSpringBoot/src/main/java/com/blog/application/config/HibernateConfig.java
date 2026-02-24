package com.blog.application.config;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;

import jakarta.persistence.EntityManagerFactory;

//@Configuration
public class HibernateConfig {

	@Bean
	public SessionFactory sessionFactory(EntityManagerFactory emf) {
		EntityManagerFactory entityManagerFactory = emf;
		SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
		return sessionFactory;
	}
}
