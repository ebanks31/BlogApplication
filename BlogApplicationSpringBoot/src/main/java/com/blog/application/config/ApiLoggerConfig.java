package com.blog.application.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.blog.application.interceptors.ApiLogger;

@Component
@Profile("!test")
public class ApiLoggerConfig implements WebMvcConfigurer {
	@Autowired
	ApiLogger apiLoggerInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(apiLoggerInterceptor);
	}
}