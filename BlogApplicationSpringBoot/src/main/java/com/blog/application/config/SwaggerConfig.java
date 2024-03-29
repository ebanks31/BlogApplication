package com.blog.application.config;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * This class sets up the configuration for Swagger API.
 */
//@Profile("!test")
@Configuration
@EnableSwagger2
@Profile("!test")
public class SwaggerConfig extends WebMvcConfigurationSupport {
	private final Logger LOGGER = LoggerFactory.getLogger(SwaggerConfig.class);

	/**
	 * Api.
	 *
	 * @return the docket
	 */
	// @Profile("prod")
	@Bean
	public Docket apiProd() {
		LOGGER.info("Setting up swagger 2");
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build().apiInfo(metaData());
	}

	/**
	 * Api info.
	 *
	 * @return the api info
	 */
	private ApiInfo metaData() {
		return new ApiInfo("My REST API", "This API is used to retrieve the blog information for a user.", "API TOS",
				"Terms of service", new Contact("Craig Marduk", "www.example.com", "myaddress@company.com"),
				"License of API", "API license URL", Collections.emptyList());
	}

	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
}