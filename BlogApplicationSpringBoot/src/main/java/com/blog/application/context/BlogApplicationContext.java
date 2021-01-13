package com.blog.application.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * The Class BlogApplicationContext.
 */
@Component
public class BlogApplicationContext implements Cloneable {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(BlogApplicationContext.class);

	/** The transaction id. */
	private String transactionId;

	/** The start time. */
	private long startTime;

	/** The end time. */
	private long endTime;

	private String uriPath;

	/** The response time. */
	private long responseTime;

	/**
	 * Clone.
	 *
	 * @return the blog application context
	 */
	public BlogApplicationContext clone() {
		try {
			return (BlogApplicationContext) super.clone();
		} catch (Exception exception) {
			LOGGER.error("Exception message {}", exception.getMessage());
			return null;
		}
	}

	/**
	 * Gets the transaction id.
	 *
	 * @return the transaction id
	 */
	public String getTransactionId() {
		return transactionId;
	}

	/**
	 * Sets the transaction id.
	 *
	 * @param transactionId the new transaction id
	 */
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * Gets the start time.
	 *
	 * @return the start time
	 */
	public long getStartTime() {
		return startTime;
	}

	/**
	 * Sets the start time.
	 *
	 * @param startTime the new start time
	 */
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	/**
	 * Gets the end time.
	 *
	 * @return the end time
	 */
	public long getEndTime() {
		return endTime;
	}

	/**
	 * Sets the end time.
	 *
	 * @param endTime the new end time
	 */
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	/**
	 * Gets the response time.
	 *
	 * @return the response time
	 */
	public long getResponseTime() {
		return responseTime;
	}

	/**
	 * Sets the response time.
	 *
	 * @param responseTime the new response time
	 */
	public void setResponseTime(long responseTime) {
		this.responseTime = responseTime;
	}

	/**
	 * Gets the uri path.
	 *
	 * @return the uri path
	 */
	public String getUriPath() {
		return uriPath;
	}

	public void setUriPath(String uriPath) {
		this.uriPath = uriPath;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "BlogApplicationContext [LOGGER=" + LOGGER + ", transactionId=" + transactionId + ", startTime="
				+ startTime + ", endTime=" + endTime + ", responseTime=" + responseTime + "]";
	}
}
