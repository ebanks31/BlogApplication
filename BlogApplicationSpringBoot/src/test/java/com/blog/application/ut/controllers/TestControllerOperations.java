package com.blog.application.ut.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.blog.application.BlogApplication;
import com.blog.application.config.TestConfig;
import com.blog.application.controllers.AccountRestController;
import com.blog.application.controllers.BlogRestController;
import com.blog.application.model.Account;
import com.blog.application.model.Blog;
import com.blog.application.model.BlogPost;
import com.blog.application.model.User;
import com.blog.application.service.IAccountService;
import com.blog.application.service.IBlogPostService;
import com.blog.application.service.IBlogService;
import com.blog.application.service.IUserService;

@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
@RunWith(SpringRunner.class)
@ComponentScan(basePackages = "com.blog.application")
@Import(AccountRestController.class)
@ContextConfiguration(classes = AccountRestController.class)
@EnableWebMvc
@SpringBootTest(classes = { BlogApplication.class, TestConfig.class })
public class TestControllerOperations {

	/** The Constant ORIGIN. */
	protected static final String ORIGIN = "origin";

	/** The mock mvc. */
	protected MockMvc mockMvc;

	@Autowired
	protected WebApplicationContext webApplicationContext;

	/** The blog service. */
	@Mock
	IBlogService blogService;

	@Mock
	IAccountService accountService;

	/** The blog post service. */
	@Mock
	IBlogPostService blogPostService;

	/** The user service. */
	@Mock
	IUserService userService;

	// Controllers
	@Autowired
	BlogRestController blogController;

	@Autowired
	AccountRestController accountController;

	@Before()
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Rule
	public TestName testName = new TestName();

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

	protected Account mockAccount() {
		Account account = new Account();
		account.setAccountId(new Long(1));
		account.setEmail("test@gmail.com");
		account.setPassword("password");
		return account;
	}

	protected List<Account> mockAccountList() {
		Account account = new Account();
		account.setAccountId(new Long(1));
		account.setEmail("test@gmail.com");
		account.setPassword("password");

		return Arrays.asList(account);
	}

	protected Blog mockBlog() {
		Blog blog = new Blog();
		blog.setBlogId(new Long(1));
		blog.setBlogTitle("blogTitle");
		blog.setBlogDescription("blogDescription");
		return blog;
	}

	protected BlogPost mockBlogPost() {
		BlogPost blogPost = new BlogPost();
		blogPost.setBlogPostId(new Long(1));
		blogPost.setBlogPostTitle("blogPostTitle");
		blogPost.setBlogPostBody("blogPostBody");
		blogPost.setBlogId(new Long(1));
		return blogPost;
	}

	protected List<Account> mockAccountList(List<Account> accountList) {
		List<Account> finalAccountList = new ArrayList<>();
		accountList.stream().forEach(x -> finalAccountList.add(x));

		return finalAccountList;
	}

	protected User mockUser() {
		User firstUser = new User();
		firstUser.setUserId(new Long(1));
		firstUser.setFirstname("John");
		firstUser.setLastname("Doe");
		return firstUser;
	}
}
