package com.blog.application.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Optional;

import org.junit.Test;

import com.blog.application.model.Account;
import com.blog.application.model.Blog;
import com.blog.application.model.BlogPost;
import com.blog.application.model.Comment;
import com.blog.application.model.User;

/** Unit tests for the repository-backed service implementations. */
public class ServiceUnitTests extends ServiceOperations {

	@Test
	public void accountServiceFindsAndDelegatesAccountOperations() {
		Account account = mockAccount();
		when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

		assertSame(account, accountService.findByAccountId(1L));
		accountService.addAccount(account);
		accountService.deleteAccount(1L);

		verify(accountRepository).save(account);
		verify(accountRepository).deleteById(1L);
	}

	@Test
	public void accountServiceEditsExistingAccount() {
		Account account = mockAccount();
		when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

		accountService.editAccount(1L, mockAccount());

		verify(accountRepository).save(account);
	}

	@Test
	public void blogServiceUsesCachedBlogsWhenAvailable() {
		Blog blog = mockBlog();
		when(blogCacheService.findAllBlogsFromCache()).thenReturn(Collections.singletonList(blog));

		assertSame(blog, blogService.findAll().get(0));
	}

	@Test
	public void blogServiceFallsBackToRepositoryWhenCacheIsEmpty() {
		Blog blog = mockBlog();
		when(blogCacheService.findAllBlogsFromCache()).thenReturn(Collections.emptyList());
		when(blogRepository.findAll()).thenReturn(Collections.singletonList(blog));

		assertSame(blog, blogService.findAll().get(0));
	}

	@Test
	public void blogServiceEditsExistingBlogFields() {
		Blog storedBlog = mockBlog();
		Blog changes = mockBlog();
		changes.setBlogTitle("Updated title");
		changes.setBlogDescription("Updated description");
		when(blogRepository.findByBlogPostId(1L)).thenReturn(Optional.of(storedBlog));

		blogService.editBlog(1L, changes);

		assertEquals("Updated title", storedBlog.getBlogTitle());
		assertEquals("Updated description", storedBlog.getBlogDescription());
		verify(blogRepository).save(storedBlog);
	}

	@Test
	public void blogPostServiceAssociatesParentBlogWhenAdding() {
		Blog blog = mockBlog();
		BlogPost blogPost = mockBlogPost();
		when(blogService.findByBlogId(1L)).thenReturn(blog);

		blogPostService.addBlogPost(blogPost, 1L);

		assertSame(blog, blogPost.getBlog());
		verify(blogPostRepository).save(blogPost);
	}

	@Test
	public void blogPostServiceDelegatesNestedOperations() {
		BlogPost blogPost = mockBlogPost();
		when(blogPostRepository.findByBlogPostIdAndBlogId(1L, 2L)).thenReturn(blogPost);

		assertSame(blogPost, blogPostService.findByBlogPostIdAndBlogId(1L, 2L));
		blogPostService.deleteBlogPost(2L, 1L);

		verify(blogPostRepository).deleteByBlogPostIdAndBlogId(2L, 1L);
	}

	@Test
	public void blogPostServiceEditsExistingPost() {
		BlogPost storedPost = mockBlogPost();
		BlogPost changes = mockBlogPost();
		changes.setBlogPostTitle("Updated title");
		changes.setBlogPostBody("Updated body");
		when(blogPostRepository.findByBlogPostId(2L)).thenReturn(storedPost);
		when(blogService.findByBlogId(1L)).thenReturn(mockBlog());

		blogPostService.editBlogPost(2L, 1L, changes);

		assertEquals("Updated title", storedPost.getBlogPostTitle());
		assertEquals("Updated body", storedPost.getBlogPostBody());
		verify(blogPostRepository).save(storedPost);
	}

	@Test
	public void commentServiceAssociatesParentIdentifiersWhenAdding() {
		Comment comment = mockComment();

		commentService.addCommentWithBlogIdAndBlogPostId(comment, 4L, 5L);

		assertEquals(Long.valueOf(4L), comment.getBlogId());
		assertEquals(Long.valueOf(5L), comment.getBlogPostId());
		verify(commentRepository).save(comment);
	}

	@Test
	public void commentServiceFindsAndDeletesNestedComment() {
		Comment comment = mockComment();
		when(commentRepository.findCommentByBlogIdAndBlogPostId(3L, 4L, 5L)).thenReturn(Optional.of(comment));

		assertSame(comment, commentService.findByCommentByBlogIdAndBlogPostId(3L, 4L, 5L));
		commentService.deleteCommentWithBlogIdAndBlogPostId(3L, 4L, 5L);

		verify(commentRepository).deleteCommentByBlogIdAndBlogPostId(3L, 4L, 5L);
	}

	@Test
	public void commentServiceEditsExistingNestedComment() {
		Comment storedComment = mockComment();
		Comment changes = mockComment();
		changes.setComment("Updated comment");
		when(commentRepository.findCommentByBlogIdAndBlogPostId(3L, 4L, 5L)).thenReturn(Optional.of(storedComment));

		commentService.editCommentByBlogIdAndBlogPostId(changes, 3L, 4L, 5L);

		assertEquals("Updated comment", storedComment.getComment());
		verify(commentRepository).save(storedComment);
	}

	@Test
	public void userServiceEditsExistingUserFields() {
		User storedUser = mockUser();
		User changes = mockUser();
		changes.setFirstname("Jane");
		changes.setLastname("Smith");
		changes.setMiddlename("Q");
		when(userRepository.findById(1L)).thenReturn(Optional.of(storedUser));

		userService.editUser(1L, changes);

		assertEquals("Jane", storedUser.getFirstname());
		assertEquals("Smith", storedUser.getLastname());
		assertEquals("Q", storedUser.getMiddlename());
		verify(userRepository).save(storedUser);
	}

	@Test
	public void userServiceDelegatesCreateAndDelete() {
		User user = mockUser();

		userService.addUser(user);
		userService.deleteUser(1L);

		verify(userRepository).save(user);
		verify(userRepository).deleteById(1L);
	}
}
