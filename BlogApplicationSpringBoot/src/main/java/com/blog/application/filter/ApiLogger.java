package com.blog.application.filter;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class ApiLogger extends HandlerInterceptorAdapter {
	private static final String REQUEST_ID = "requestId";
	private static final Logger LOGGER = LoggerFactory.getLogger(ApiLogger.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String requestId = UUID.randomUUID().toString();
		log(request, response, requestId);

		long startTime = System.currentTimeMillis();
		long seconds = TimeUnit.MILLISECONDS.toSeconds(startTime);

		request.setAttribute("startTime", startTime);
		request.setAttribute(REQUEST_ID, requestId);

		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		super.afterCompletion(request, response, handler, ex);
		long startTime = (Long) request.getAttribute("startTime");
		long endTime = System.currentTimeMillis();
		long executeTime = endTime - startTime;
		LOGGER.info("requestId {}, Handle :{} , request take time: {}", request.getAttribute(REQUEST_ID), handler,
				executeTime);
		long seconds = TimeUnit.MILLISECONDS.toSeconds(executeTime);

		LOGGER.info("requestId {}, Handle :{} , request take time: {}", request.getAttribute(REQUEST_ID), handler,
				seconds);
	}

	private void log(HttpServletRequest request, HttpServletResponse response, String requestId) {
		LOGGER.info("requestId {}, host {}  HttpMethod: {}, URI : {}", requestId, request.getHeader("host"),
				request.getMethod(), request.getRequestURI());
	}
}