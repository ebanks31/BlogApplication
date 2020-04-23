package com.blog.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * This class sets up the configuration for Rest Template.
 */
@Configuration
public class RestTemplateConfig extends WebMvcConfigurationSupport {

	@Bean
	public RestTemplate rest() {
		return new RestTemplate();
	}
}