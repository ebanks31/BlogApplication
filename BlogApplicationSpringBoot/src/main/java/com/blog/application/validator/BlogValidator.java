package com.blog.application.validator;

import java.util.List;
import java.util.function.Predicate;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.blog.application.model.Blog;

import io.micrometer.core.instrument.util.StringUtils;

@Service
public class BlogValidator extends BaseBlogValidator {

	@Override
	public boolean validateBlog(Blog blog) {
		boolean valid = false;

		if (blog != null && ((blog.getBlogId() != null && blog.getBlogId() > 0L)
				&& (blog.getAccountId() != null && blog.getAccountId() > 0L)
				&& StringUtils.isNotBlank(blog.getBlogTitle()))) {
			valid = true;
		}

		return valid;
	}

	@Override
	public boolean validateBlogList(List<Blog> bloglist) {
		boolean valid = false;

		if (!CollectionUtils.isEmpty(bloglist)) {
			Predicate<Blog> blogPredicate = blog -> blog != null && (blog.getBlogId() != null && blog.getBlogId() > 0L)
					&& (blog.getAccountId() != null && blog.getAccountId() > 0L)
					&& StringUtils.isNotBlank(blog.getBlogTitle());

			valid = bloglist.stream().allMatch(blogPredicate);
		}

		return valid;
	}

}
