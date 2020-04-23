package com.blog.application.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.application.model.Blog;
import com.blog.application.repositories.BlogRepository;
import com.blog.application.service.IBlogService;

/**
 * The Class BlogService.
 */
@Service
public class BlogService implements IBlogService {

	/** The logger. */
	private final Logger LOGGER = LoggerFactory.getLogger(BlogService.class);

	/** The repository. */
	@Autowired
	private BlogRepository repository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.blog.application.service.IBlogService#findAll()
	 */
	@Override
	public List<Blog> findAll() {
		return repository.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.blog.application.service.IBlogService#findByBlogId(long)
	 */
	@Override
	public Blog findByBlogId(long id) {
		Optional<Blog> value = repository.findById(id);

		Blog blog = null;
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
	public void addBlog(Blog blog) {
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
	public void editBlog(long blogId, Blog blog) {
		LOGGER.info("blogId {}", blogId);

		if (blogId != 0) {
			Optional<Blog> blogOptional = repository.findByBlogPostId(blogId);
			Blog retrievedBlog = null;

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
