package com.blog.application.validator;

import java.util.List;
import java.util.function.Predicate;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.blog.application.model.Comment;

@Service
public class CommentValidator extends BaseCommentValidator {

	@Override
	public boolean validateComment(Comment comment) {
		boolean valid = false;

		if (comment != null && (comment.getCommentId() != null && comment.getCommentId() > 0L)
				&& (comment.getBlogId() != null && comment.getBlogId() > 0L)
				&& (comment.getBlogPostId() != null && comment.getBlogPostId() > 0L)) {
			valid = true;
		}

		return valid;
	}

	@Override
	public boolean validateCommentList(List<Comment> commentList) {
		boolean valid = false;

		if (!CollectionUtils.isEmpty(commentList)) {
			Predicate<Comment> commentPredicate = comment -> comment != null
					&& ((comment.getCommentId() != null && comment.getCommentId() > 0L)
							&& (comment.getBlogId() != null && comment.getBlogId() > 0L)
							&& (comment.getBlogPostId() != null && comment.getBlogPostId() > 0L));

			valid = commentList.stream().allMatch(commentPredicate);
		}

		return valid;
	}

}
