package com.blog.application.cache;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.blog.application.model.Blog;
import com.blog.application.repositories.BlogRepository;

/**
 * The Class BlogCacheService.
 */
@Service
@EnableScheduling
public class BlogCacheService {

	private static final String CRON_SCHEDULE = "0 59 23 * * ?";

	/** The logger. */
	private final Logger LOGGER = LoggerFactory.getLogger(BlogCacheService.class);

	/** The repository. */
	@Autowired
	private BlogRepository repository;

	/**
	 * Finds all the blogs from cache.
	 *
	 * @return the list
	 */
	@Cacheable("blogs")
	public List<Blog> findAllBlogsFromCache() {
		LOGGER.info("Retrieving all the blogs from the cache");
		return repository.findAllBlogs();
	}

	@Scheduled(cron = CRON_SCHEDULE)
	@CacheEvict(value = "blogs", allEntries = true)
	public void clearingBlogCache() {
		LOGGER.info("Evicting the cache");
	}
}