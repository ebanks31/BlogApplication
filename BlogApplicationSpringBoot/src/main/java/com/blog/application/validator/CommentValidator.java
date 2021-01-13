package com.blog.application.validator;

import java.util.List;
import java.util.function.Predicate;

import com.blog.application.model.Comment;

public class CommentValidator extends BaseCommentValidator {

	@Override
	public boolean validateComment(Comment comment) {
		boolean valid = true;

		if (comment != null && (comment.getCommentId() > 0 || comment.getBlogId() > 0 || comment.getBlogPostId() > 0)) {
			valid = false;
		}

		return valid;
	}

	@Override
	public boolean validateCommentList(List<Comment> commentList) {
		boolean valid = true;

		Predicate<Comment> commentPredicate = comment -> comment != null
				&& (comment.getCommentId() > 0 || comment.getBlogId() > 0 || comment.getBlogPostId() > 0);

		valid = commentList.stream().noneMatch(commentPredicate);

		return valid;
	}

}
