package com.blog.application.validator;

import java.util.List;
import java.util.function.Predicate;

import com.blog.application.model.Blog;

import io.micrometer.core.instrument.util.StringUtils;

public class BlogValidator extends BaseBlogValidator {

	@Override
	public boolean validateBlog(Blog blog) {
		boolean valid = true;

		if (blog != null
				&& (blog.getBlogId() > 0 || blog.getAccountId() > 0 || StringUtils.isBlank(blog.getBlogTitle()))) {
			valid = false;
		}

		return valid;
	}

	@Override
	public boolean validateBlogList(List<Blog> bloglist) {
		boolean valid = true;

		Predicate<Blog> blogPredicate = blog -> blog != null
				&& (blog.getBlogId() > 0 || blog.getAccountId() > 0 || StringUtils.isBlank(blog.getBlogTitle()));

		valid = bloglist.stream().noneMatch(blogPredicate);

		return valid;
	}

}
