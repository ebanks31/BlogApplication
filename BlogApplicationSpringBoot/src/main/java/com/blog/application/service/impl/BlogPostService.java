package com.blog.application.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.application.model.BlogPost;
import com.blog.application.repositories.BlogPostRepository;
import com.blog.application.service.IBlogPostService;

/**
 * The Class BlogPostService.
 */
@Service
public class BlogPostService implements IBlogPostService {

	/** The logger. */
	private final Logger LOGGER = LoggerFactory.getLogger(BlogPostService.class);

	/** The repository. */
	@Autowired
	private BlogPostRepository repository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.blog.application.service.IBlogPostService#findAll()
	 */
	@Override
	public List<BlogPost> findAll() {

		List<BlogPost> blogPosts = repository.findAll();
		return blogPosts;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.blog.application.service.IBlogPostService#findByBlogId(long)
	 */
	@Override
	public List<BlogPost> findByBlogId(long id) {
		List<BlogPost> blogPosts = repository.findAllBlogPostsByBlogId(id);
		return blogPosts;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.blog.application.service.IBlogPostService#findByBlogPostIdAndBlogId(long,
	 * long)
	 */
	@Override
	public BlogPost findByBlogPostIdAndBlogId(long blogId, long blogPostId) {
		BlogPost blogPost = repository.findByBlogPostIdAndBlogId(blogId, blogPostId);
		return blogPost;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.blog.application.service.IBlogPostService#findByBlogPostId(long)
	 */
	@Override
	public BlogPost findByBlogPostId(long blogPostId) {
		Optional<BlogPost> value = repository.findById(blogPostId);
		BlogPost blogPost = null;

		if (value.isPresent()) {
			blogPost = value.get();
		}

		return blogPost;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.blog.application.service.IBlogPostService#addBlogPost(com.blog.
	 * application.model.BlogPost)
	 */
	@Override
	public void addBlogPost(BlogPost blogPost) {
		LOGGER.info("addBlogPost()");

		repository.save(blogPost);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.blog.application.service.IBlogPostService#deleteBlogPost(long)
	 */
	@Override
	public void deleteBlogPost(long blogPostId) {
		repository.deleteById(blogPostId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.blog.application.service.IBlogPostService#editBlogPost(long)
	 */
	@Override
	public void editBlogPost(long blogPostId, BlogPost blogpost) {
		LOGGER.info("editBlogPost()");

		LOGGER.info("blogPostId {}", blogPostId);

		if (blogPostId != 0) {
			// BlogPost blogPostOptional = repository.findByBlogPostId(blogPostId);
			BlogPost retrievedBlogPost = repository.findByBlogPostId(blogPostId);

			/*
			 * BlogPost retrievedBlogPost = repository.findByBlogPostId(blogPostId);;
			 * 
			 * 
			 * if (blogPostOptional.isPresent()) { retrievedBlogPost =
			 * blogPostOptional.get(); }
			 */

			if (retrievedBlogPost != null) {
				LOGGER.info("retrievedBlogPost {}", retrievedBlogPost);
				LOGGER.info("blogpost.getVersion() {}", blogpost.getVersion());

				retrievedBlogPost.setBlogPostTitle(blogpost.getBlogPostTitle());
				retrievedBlogPost.setBlogPostBody(blogpost.getBlogPostBody());

				LOGGER.info("retrievedBlogPost.getBlogPostTitle() {}", retrievedBlogPost.getBlogPostTitle());
				LOGGER.info("retrievedBlogPost.getBlogPostBody() {}", retrievedBlogPost.getBlogPostBody());
				LOGGER.info("retrievedBlogPost.getVersion() {}", retrievedBlogPost.getVersion());

				LOGGER.info("retrievedBlogPost.getBlogPostId() {}", retrievedBlogPost.getBlogPostId());
				LOGGER.info("retrievedBlogPost.getLastUpdateDate() {}", retrievedBlogPost.getLastUpdateDate());
				LOGGER.info("retrievedBlogPost.getBlogId() {}", retrievedBlogPost.getBlogId());

				repository.save(retrievedBlogPost);
			} else {
				LOGGER.info("No blogPost was found");
			}
		}
	}
}
