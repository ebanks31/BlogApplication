package com.blog.application.aspects;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

/**
 * The Class BlogLoggingAspect.
 */
@Aspect
public class BlogLoggingAspect {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(BlogLoggingAspect.class);
	private static final String JOINPOINT_SIGNATURE_NAME = "Getting joinpoint signature name: {}";

	@Autowired
	private RestTemplate restTemplate;

	@Value(value = "${spring.boot.kafka.address}")
	private String springbootKafkaAddress;

	@Autowired
	Environment environment;

	/**
	 * Log before.
	 *
	 * @param joinPoint the join point
	 */
	@Before("execution(* com.blog.application.controller(..))")
	public void logBefore(JoinPoint joinPoint) {

		LOGGER.info("logBefore() runs");
		LOGGER.info(JOINPOINT_SIGNATURE_NAME, joinPoint.getSignature().getName());
	}

	/**
	 * Log after.
	 *
	 * @param joinPoint the join point
	 */
	@After("execution(* com.blog.application.controller(..))")
	public void logAfter(JoinPoint joinPoint) {

		LOGGER.info("logAfter() runs");
		LOGGER.info(JOINPOINT_SIGNATURE_NAME, joinPoint.getSignature().getName());

	}

	/**
	 * Log around.
	 *
	 * @param joinPoint the join point
	 * @throws Throwable the throwable
	 */
	@Around("execution(* com.blog.application.controller(..))")
	public void logAround(ProceedingJoinPoint joinPoint) throws Throwable {
		long startTime = System.currentTimeMillis();
		joinPoint.proceed();
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();

		LOGGER.info("Method Name is {}", method.getName());
		LOGGER.info("Method Types is {}", method);

		long timeTaken = System.currentTimeMillis() - startTime;
		LOGGER.info("Time Taken by {} is {}", joinPoint, timeTaken);

		HttpHeaders headers = new HttpHeaders();

		JSONObject messageJsonObject = populateMessage();

		// can set the content Type
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>(messageJsonObject.toString(), headers);

		// Sends Transaction time to Kafka with the method name
		restTemplate.postForObject(springbootKafkaAddress, entity, String.class);

	}

	/**
	 * Populate message.
	 *
	 * @return the JSON object
	 * @throws UnknownHostException the unknown host exception
	 */
	private JSONObject populateMessage() throws UnknownHostException {
		JSONObject messageJsonObject = new JSONObject();
		messageJsonObject.put("id_number", 1);
		messageJsonObject.put("hostAddress", InetAddress.getLocalHost().toString());
		messageJsonObject.put("hostName", InetAddress.getLocalHost().getHostName());
		messageJsonObject.put("hostPort", environment.getProperty("server.port"));
		messageJsonObject.put("status", "TimeTrack");
		messageJsonObject.put("method", "getBlogs");
		messageJsonObject.put("urlPath", "/blogs");
		messageJsonObject.put("projectName", "TestProject");
		messageJsonObject.put("statusCode", HttpStatus.OK.value());

		UUID uuid = UUID.randomUUID();
		messageJsonObject.put("uniqueIdentifier", uuid.toString());
		return messageJsonObject;
	}

	// TODO: Need to add aspect loggers to log to make sure all Service layers is
	// running correctly and efficiently.
}