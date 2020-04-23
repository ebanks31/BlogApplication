package com.blog.application.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.blog.application.model.Blog;

@RepositoryRestResource
public interface BlogRepository extends JpaRepository<Blog, Long> {

	@Query(value = "select * from BlogPost t where t.blog_post_id = ?1 LIMIT 1", nativeQuery = true)
	Optional<Blog> findByBlogPostId(long blogId);
}
