package com.blog.application.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * The Class SwaggerConfig.
 */
@Configuration
@EnableSwagger2
@Profile("!test")
public class SwaggerConfig {

	/**
	 * Api.
	 *
	 * @return the docket
	 */
	@Profile("prod")
	@Bean
	public Docket apiProd() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build().apiInfo(apiInfo());
	}

	/**
	 * Api.
	 *
	 * @return the docket
	 */
	@Profile("dev")
	@Bean
	public Docket apiDev() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build().apiInfo(apiInfo());
	}

	/**
	 * Api info.
	 *
	 * @return the api info
	 */
	private ApiInfo apiInfo() {
		return new ApiInfo("My REST API", "This API is used to retrieve the blog information for a user.", "API TOS",
				"Terms of service", new Contact("Craig Marduk", "www.example.com", "myeaddress@company.com"),
				"License of API", "API license URL", Collections.emptyList());
	}
}
