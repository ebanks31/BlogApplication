package com.blog.application.validator;

import java.util.List;

import com.blog.application.model.BlogPost;

public abstract class BaseBlogPostValidator implements BaseValidator {
	public abstract boolean validateBlogPost(BlogPost blogPost);

	public abstract boolean validateBlogPostList(List<BlogPost> blogPostList);
}
