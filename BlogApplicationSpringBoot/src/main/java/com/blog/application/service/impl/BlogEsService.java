package com.blog.application.service.impl;

import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.IterableUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.application.elasticsearch.repositories.BlogEsRepository;
import com.blog.application.model.elasticsearch.BlogEs;
import com.blog.application.service.IBlogEsService;

/**
 * The Class BlogService.
 */
@Service
public class BlogEsService implements IBlogEsService {

	/** The logger. */
	private final Logger LOGGER = LoggerFactory.getLogger(BlogEsService.class);

	/** The repository. */
	@Autowired
	private BlogEsRepository repository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.blog.application.service.IBlogService#findAll()
	 */
	@Override
	public List<BlogEs> findAll() {
		return IterableUtils.toList(repository.findAll());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.blog.application.service.IBlogService#findByBlogId(long)
	 */
	@Override
	public BlogEs findByBlogId(long id) {
		Optional<BlogEs> value = repository.findById(id);
		BlogEs blog = null;
		// ...

		if (value.isPresent()) {
			blog = value.get();
		}
		return blog;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.blog.application.service.IBlogService#addBlog(com.blog.application.model.
	 * Blog)
	 */
	@Override
	public void addBlog(BlogEs blog) {
		repository.save(blog);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.blog.application.service.IBlogService#deleteBlog(long)
	 */
	@Override
	public void deleteBlog(long blogId) {
		repository.deleteById(blogId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.blog.application.service.IBlogService#editBlog(long)
	 */
	@Override
	public void editBlog(long blogId, BlogEs blog) {
		LOGGER.info("blogId {}", blogId);

		if (blogId != 0) {
			Optional<BlogEs> blogOptional = repository.findById(blogId);
			BlogEs retrievedBlog = null;

			if (blogOptional.isPresent()) {
				retrievedBlog = blogOptional.get();
			}

			if (retrievedBlog != null) {
				retrievedBlog.setBlogTitle(blog.getBlogTitle());
				retrievedBlog.setBlogDescription(blog.getBlogDescription());

				LOGGER.info("blog.getBlogTitle() {}", blog.getBlogTitle());
				LOGGER.info("blog.getBlogDescription() {}", blog.getBlogDescription());

				repository.save(retrievedBlog);
			} else {
				LOGGER.info("No blog was found");
			}
		}
	}
}
