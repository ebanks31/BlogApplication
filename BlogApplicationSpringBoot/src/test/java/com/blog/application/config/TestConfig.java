package com.blog.application.config;

import static org.mockito.Mockito.mock;

import org.springframework.context.annotation.Bean;

import com.blog.application.interceptors.ApiLogger;
import com.hazelcast.core.HazelcastInstance;

public class TestConfig {

	@Bean
	public ApiLogger apiLogger() {
		return mock(ApiLogger.class);
	}

	@Bean
	public HazelcastInstance mockHazelCaseInstance() {
		return mock(HazelcastInstance.class);
	}
}
