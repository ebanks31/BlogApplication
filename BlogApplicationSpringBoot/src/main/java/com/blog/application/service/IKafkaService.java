package com.blog.application.service;

import java.net.UnknownHostException;
import java.util.List;

import com.blog.application.model.Blog;

/**
 * Defines the contract for publishing blog data to the configured messaging
 * endpoint.
 */
public interface IKafkaService {
	void sendBlogsToKafka(long startTime, List<Blog> blogs) throws UnknownHostException;
}
