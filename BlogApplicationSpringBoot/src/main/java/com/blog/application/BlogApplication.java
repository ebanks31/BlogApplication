package com.blog.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * The main class for starting the blogging application.
 */
//@SpringBootApplication(exclude = { ElasticsearchAutoConfiguration.class,
// ElasticSearchRestHealthIndicatorAutoConfiguration.class })
//@EnableElasticsearchRepositories(basePackages = "com.blog.application.elasticsearch.repositories")
@SpringBootApplication
@EnableJpaRepositories(basePackages = { "com.blog.application.repositories" })
@EnableCaching
public class BlogApplication {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}
}