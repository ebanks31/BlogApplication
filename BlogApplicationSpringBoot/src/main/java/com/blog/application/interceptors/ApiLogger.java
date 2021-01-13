package com.blog.application.interceptors;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.blog.application.context.BlogApplicationContext;

@Component
public class ApiLogger implements HandlerInterceptor {
	private static final String REQUEST_ID = "requestId";
	private static final Logger LOGGER = LoggerFactory.getLogger(ApiLogger.class);

	@Autowired
	BlogApplicationContext blogApplicationContext;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String requestId = UUID.randomUUID().toString();
		log(request, response, requestId);

		blogApplicationContext.setTransactionId(requestId);

		String uriPath = request.getRequestURI();

		blogApplicationContext.setUriPath(uriPath);

		long startTime = System.currentTimeMillis();
		long seconds = TimeUnit.MILLISECONDS.toSeconds(startTime);

		request.setAttribute("startTime", startTime);
		request.setAttribute(REQUEST_ID, requestId);

		blogApplicationContext.setStartTime(startTime);
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// super.afterCompletion(request, response, handler, ex);
		// long startTime = (Long) request.getAttribute("startTime");
		long startTime = blogApplicationContext.getStartTime();

		long endTime = System.currentTimeMillis();
		long executeTime = endTime - startTime;

		blogApplicationContext.setResponseTime(executeTime);

		LOGGER.info("transaction Id: {}, Handle :{} , request take time: {}", blogApplicationContext.getTransactionId(),
				handler, executeTime);
		long seconds = TimeUnit.MILLISECONDS.toSeconds(executeTime);

		LOGGER.info("transaction Id: {}, Handle :{} , request take time: {}", blogApplicationContext.getTransactionId(),
				handler, seconds);
	}

	private void log(HttpServletRequest request, HttpServletResponse response, String requestId) {
		LOGGER.info("transaction Id:  {}, host {}  HttpMethod: {}, URI : {}", requestId, request.getRemoteHost(),
				request.getMethod(), request.getRequestURI());
	}
}