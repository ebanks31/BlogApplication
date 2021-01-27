package com.blog.application.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.blog.application.cache.BlogCacheService;
import com.blog.application.model.Account;
import com.blog.application.model.Blog;
import com.blog.application.model.BlogPost;
import com.blog.application.model.Comment;
import com.blog.application.model.User;
import com.blog.application.repositories.AccountRepository;
import com.blog.application.repositories.BlogPostRepository;
import com.blog.application.repositories.BlogRepository;
import com.blog.application.repositories.CommentRepository;
import com.blog.application.repositories.UserRepository;

public class ServiceOperations {

	@Rule
	public TestWatcher testWatcher = new TestWatcher() {
		@Override
		protected void starting(final Description description) {
			String methodName = description.getMethodName();
			String className = description.getClassName();
			className = className.substring(className.lastIndexOf('.') + 1);
			System.err.println("Starting JUnit-test: " + className + " " + methodName);
		}
	};

	@InjectMocks
	protected BlogService blogService;

	@InjectMocks
	protected BlogPostService blogPostService;

	@InjectMocks
	protected AccountService accountService;

	@InjectMocks
	protected CommentService commentService;

	@InjectMocks
	protected UserService userService;

	@Mock
	protected BlogCacheService blogCacheService;

	@Mock
	BlogRepository blogRepository;

	@Mock
	BlogPostRepository blogPostRepository;

	@Mock
	AccountRepository accountRepository;

	@Mock
	CommentRepository commentRepository;

	@Mock
	UserRepository userRepository;

	@Before()
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	protected Blog mockBlog() {
		Blog blog = new Blog();
		blog.setBlogId(new Long(1));
		blog.setBlogTitle("blogTitle");
		blog.setBlogDescription("blogDescription");

		return blog;
	}

	protected List<Blog> mockBlogList() {
		List<Blog> blogList = new ArrayList<>();

		Blog blog = mockBlog();
		blogList.add(blog);

		return blogList;
	}

	protected List<Blog> mockBlogList(Blog blog) {
		List<Blog> blogList = new ArrayList<>();
		blogList.add(blog);
		return blogList;
	}

	protected Account mockAccount() {
		Account account = new Account();
		account.setAccountId(new Long(1));
		account.setEmail("test@gmail.com");
		account.setPassword("password");
		account.setUser(mockUser());
		return account;
	}

	protected List<Account> mockAccountList() {
		List<Account> accountList = new ArrayList<>();

		Account account = mockAccount();
		accountList.add(account);

		return accountList;
	}

	protected List<Account> mockAccountList(Account account) {
		account.setUser(mockUser());

		List<Account> accountList = new ArrayList<>();
		accountList.add(account);
		return accountList;
	}

	protected BlogPost mockBlogPost() {
		BlogPost blogPost = new BlogPost();
		blogPost.setBlogPostId(new Long(1));
		blogPost.setBlogPostTitle("BlogPostTitle");
		blogPost.setStatus("TestStatus");
		blogPost.setBlogPostBody("BlogPostBody");
		return blogPost;
	}

	protected List<BlogPost> mockBlogPostList() {
		List<BlogPost> blogPostList = new ArrayList<>();

		BlogPost blogPost = mockBlogPost();
		blogPostList.add(blogPost);

		return blogPostList;
	}

	protected List<BlogPost> mockBlogPostList(BlogPost blogPost) {
		List<BlogPost> blogPostList = new ArrayList<>();
		blogPostList.add(blogPost);
		return blogPostList;
	}

	protected User mockUser() {
		User user = new User();
		user.setUserId(new Long(1));
		user.setFirstname("John");
		user.setLastname("Doe");
		return user;
	}

	protected List<User> mockUserList() {
		List<User> userList = new ArrayList<>();

		User user = mockUser();
		userList.add(user);

		return userList;
	}

	protected List<User> mockUserList(User user) {
		List<User> userList = new ArrayList<>();
		userList.add(user);
		return userList;
	}

	protected Comment mockComment() {
		Comment comment = new Comment();
		comment.setCommentId(1L);
		comment.setComment("Comment");
		comment.setStatus("TestStatus");
		return comment;
	}

	protected List<Comment> mockCommentList() {
		List<Comment> commentList = new ArrayList<>();

		Comment comment = mockComment();
		commentList.add(comment);

		return commentList;
	}

	protected List<Comment> mockCommentList(Comment comment) {
		List<Comment> commentList = new ArrayList<>();
		commentList.add(comment);
		return commentList;
	}
}
