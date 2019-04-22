package com.blog.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.blog.application.model.Comment;

@RepositoryRestResource
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
