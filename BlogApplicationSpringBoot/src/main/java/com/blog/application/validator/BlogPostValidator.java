package com.blog.application.validator;

import java.util.List;
import java.util.function.Predicate;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.blog.application.model.BlogPost;

@Service
public class BlogPostValidator extends BaseBlogPostValidator {

	@Override
	public boolean validateBlogPost(BlogPost blogPost) {
		boolean valid = false;

		if (blogPost != null && ((blogPost.getBlogPostId() != null && blogPost.getBlogPostId() > 0L)
				&& (blogPost.getBlogId() != null && blogPost.getBlogId() > 0L))) {
			valid = true;
		}

		return valid;
	}

	@Override
	public boolean validateBlogPostList(List<BlogPost> blogPostlist) {
		boolean valid = false;

		if (!CollectionUtils.isEmpty(blogPostlist)) {
			Predicate<BlogPost> blogPostPredicate = blogPost -> ((blogPost != null && blogPost.getBlogPostId() != null
					&& blogPost.getBlogPostId() > 0L) && (blogPost.getBlogId() != null && blogPost.getBlogId() > 0L));

			valid = blogPostlist.stream().allMatch(blogPostPredicate);
		}

		return valid;
	}

}
