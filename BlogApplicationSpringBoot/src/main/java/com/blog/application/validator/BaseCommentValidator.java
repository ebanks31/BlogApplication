package com.blog.application.validator;

import java.util.List;

import com.blog.application.model.Comment;

public abstract class BaseCommentValidator implements BaseValidator {
	public abstract boolean validateComment(Comment comment);

	public abstract boolean validateCommentList(List<Comment> commentList);
}
