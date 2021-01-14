package com.blog.application.validator;

import java.util.List;
import java.util.function.Predicate;

import org.apache.commons.collections.CollectionUtils;

import com.blog.application.model.BlogPost;

public class BlogPostValidator extends BaseBlogPostValidator {

	@Override
	public boolean validateBlogPost(BlogPost blogPost) {
		boolean valid = true;

		if (blogPost != null && (blogPost.getBlogPostId() > 0 || blogPost.getBlogId() > 0)) {
			valid = false;
		}

		return valid;
	}

	@Override
	public boolean validateBlogPostList(List<BlogPost> blogPostlist) {
		boolean valid = true;

		if (!CollectionUtils.isEmpty(blogPostlist)) {
			Predicate<BlogPost> blogPostPredicate = blogPost -> blogPost != null
					&& (blogPost.getBlogPostId() > 0 || blogPost.getBlogId() > 0);

			valid = blogPostlist.stream().noneMatch(blogPostPredicate);
		} else {
			valid = false;
		}

		return valid;
	}

}
