package com.blog.application.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class BlogLoggingAspect.
 */
@Aspect
public class BlogLoggingAspect {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(BlogLoggingAspect.class);
	private static final String JOINPOINT_SIGNATURE_NAME = "Getting joinpoint signature name: {}";

	/**
	 * Log before.
	 *
	 * @param joinPoint the join point
	 */
	@Before("execution(*com.blog.application.service.impl.findAll(..))")
	public void logBefore(JoinPoint joinPoint) {

		LOGGER.info("logBefore() runs");
		LOGGER.info(JOINPOINT_SIGNATURE_NAME, joinPoint.getSignature().getName());
	}

	/**
	 * Log after.
	 *
	 * @param joinPoint the join point
	 */
	@After("execution(*com.blog.application.service.impl.findAll(..))")
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
	@Around("execution(*com.blog.application.service.impl.findAll(..))")
	public void logAround(ProceedingJoinPoint joinPoint) throws Throwable {

		LOGGER.info("logAround() runs");
		LOGGER.info(JOINPOINT_SIGNATURE_NAME, joinPoint.getSignature().getName());
		// LOGGER.info("Getting joinpoint arguments : {}",
		// Arrays.toString(joinPoint.getArgs()));

		LOGGER.info("Around before runs");
		joinPoint.proceed(); // continue on the intercepted method
		LOGGER.info("Around after runs");
	}

	// TODO: Need to add aspect loggers to log to make sure all Service layers is
	// running correctly and efficiently.
}