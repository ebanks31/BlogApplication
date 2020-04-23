package com.blog.application.config;

import javax.persistence.EntityManagerFactory;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;

//@Configuration
public class HibernateConfig {

	@Bean
	public SessionFactory sessionFactory(EntityManagerFactory emf) {
		EntityManagerFactory entityManagerFactory = emf;
		SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
		return sessionFactory;
	}
}
