package com.blog.application.service.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

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
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.blog.application.model.Blog;
import com.blog.application.service.IKafkaService;
import com.blog.application.utils.Helper;

@Service
public class KafkaService implements IKafkaService {
	private final Logger LOGGER = LoggerFactory.getLogger(KafkaService.class);

	private static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";

	@Autowired
	private RestTemplate restTemplate;

	@Value(value = "${spring.boot.kafka.address}")
	private String springbootKafkaAddress;

	@Autowired
	Environment environment;

	@Override
	public void sendBlogsToKafka(long startTime, List<Blog> blogs) throws UnknownHostException {
		HttpHeaders headers = new HttpHeaders();

		// can set the content Type
		headers.setContentType(MediaType.APPLICATION_JSON);

		// put your customBean to header
		JSONObject messageJsonObject = new JSONObject();
		messageJsonObject.put("id_number", 1);
		messageJsonObject.put("hostAddress", InetAddress.getLocalHost().toString());
		messageJsonObject.put("hostName", InetAddress.getLocalHost().getHostName());
		messageJsonObject.put("hostPort", environment.getProperty("server.port"));
		messageJsonObject.put("status", "Failure");
		messageJsonObject.put("method", "getBlogs");
		messageJsonObject.put("urlPath", "/blogs");
		messageJsonObject.put("projectName", "TestProject");
		messageJsonObject.put("statusCode", HttpStatus.OK.value());

		UUID uuid = UUID.randomUUID();
		messageJsonObject.put("uniqueIdentifier", uuid.toString());

		String blogsJSON = Helper.createJson(blogs).toString();

		messageJsonObject.put("details", blogsJSON);

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(TIMESTAMP_FORMAT);
		LocalDateTime now = LocalDateTime.now(Clock.systemUTC());
		LOGGER.info("Date time formatter: {}", dateTimeFormatter.format(now));

		long endTime = System.currentTimeMillis();
		long executeTime = endTime - startTime;

		LOGGER.info("executeTime: {}", executeTime);
		final float sec;

		sec = executeTime / 1000.0f;
		LOGGER.info("sec: {}", sec);

		messageJsonObject.put("apiTransactionTime", sec);

		HttpEntity<String> entity = new HttpEntity<>(messageJsonObject.toString(), headers);
		restTemplate.postForObject(springbootKafkaAddress, entity, String.class);
	}
}
