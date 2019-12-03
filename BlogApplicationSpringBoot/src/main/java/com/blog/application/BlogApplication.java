package com.blog.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.elasticsearch.ElasticSearchRestHealthIndicatorAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * The Class BlogApplication.
 */
@SpringBootApplication(exclude = { ElasticsearchAutoConfiguration.class,
		ElasticSearchRestHealthIndicatorAutoConfiguration.class })
//@EnableElasticsearchRepositories(basePackages = "com.blog.application.elasticsearch.repositories")
@EnableJpaRepositories(basePackages = { "com.blog.application.repositories" })
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
