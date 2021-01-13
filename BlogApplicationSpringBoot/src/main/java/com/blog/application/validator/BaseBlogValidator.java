package com.blog.application.validator;

import java.util.List;

import com.blog.application.model.Blog;

public abstract class BaseBlogValidator extends BaseValidator {
	public abstract boolean validateBlog(Blog blog);

	public abstract boolean validateBlogList(List<Blog> blogList);
}
