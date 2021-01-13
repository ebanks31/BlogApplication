package com.blog.application.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.blog.application.model.Comment;

@RepositoryRestResource
public interface CommentRepository extends JpaRepository<Comment, Long> {
	@Query(value = "select * from Comment c where c.comment_id = ?1 AND c.blog_id = ?2 AND c.blog_post_id = ?3 LIMIT 1", nativeQuery = true)
	Optional<Comment> findCommentByBlogIdAndBlogPostId(long commentId, long blogId, long blogPostId);

	@Query(value = "DELETE from Comment c where c.comment_id = ?1 AND c.blog_id = ?2 AND c.blog_post_id = ?3", nativeQuery = true)
	void deleteCommentByBlogIdAndBlogPostId(long commentId, long blogId, long blogPostId);
}
