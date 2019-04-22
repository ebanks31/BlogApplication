package com.blog.application.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.blog.application.model.BlogPost;

@RepositoryRestResource
public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {

	// @Query("from BlogPost t where t.blogId = :blogId")
	// List<BlogPost> findAllBlogPostsByBlogId(@Param("blogId") long blogId);

	// @Query("from BlogPost t where t.blogId = :blogId and t.blogPostId =
	// :blogPostId")
	// BlogPost findByBlogPostIdAndBlogId(@Param("blogId") long blogId,
	// @Param("blogPostId") long blogPostId);

	@Query(value = "select * from Blog_Post t where t.blog_id = ?1", nativeQuery = true)
	List<BlogPost> findAllBlogPostsByBlogId(long blogId);

	@Query(value = "select * from Blog_Post t where t.blog_id = ?1 and t.blog_post_id = ?2 LIMIT 1", nativeQuery = true)
	BlogPost findByBlogPostIdAndBlogId(long blogId, long blogPostId);

	@Query(value = "select * from Blog_Post t where t.blog_post_id = ?1 LIMIT 1", nativeQuery = true)
	BlogPost findByBlogPostId(Long blogPostId);
}
