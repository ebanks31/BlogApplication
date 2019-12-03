import { BlogModel } from "./blog.component";
import { BlogPostModel } from "./blogpost.component";
import { TestBed, getTestBed } from "@angular/core/testing";
import { BlogService } from "./blog.service";
import {
  HttpClientTestingModule,
  HttpTestingController
} from "@angular/common/http/testing";
import { of } from 'rxjs';
import { HttpErrorResponse  } from '@angular/common/http';

describe("BlogService", () => {
  let blogService: BlogService;
  let httpTestingController: HttpTestingController;
  let httpClientSpy: { get: jasmine.Spy };
  let blogServiceSpy: BlogService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [BlogService],
      imports: [HttpClientTestingModule]
    });

    // We inject our service (which imports the HttpClient) and the Test Controller
    httpTestingController = TestBed.get(HttpTestingController);
    blogService = TestBed.get(BlogService);
    httpClientSpy = jasmine.createSpyObj("HttpClient", ["get"]);
    blogServiceSpy = new BlogService(<any> httpClientSpy);
    //jasmine.DEFAULT_TIMEOUT_INTERVAL = 100000;
  });

  afterEach(() => {
    httpTestingController.verify();
  });

  const dummyBlog = [
    { blogId: 1 },
    { blogDescription: "description" },
    { blog_created_date: "2011-09-09" },
    { blog_terminated_date: "2011-12-09" },
    { status: "Active" },
    { accountId: 1 },
    { last_updated_date: "2011-11-09" }
  ];

  const dummyBlog2 = [
    { blogId: 2 },
    { blogDescription: "description2" },
    { blog_created_date: "2011-09-09" },
    { blog_terminated_date: "2011-12-09" },
    { status: "Active" },
    { accountId: 1 },
    { last_updated_date: "2011-11-09" }
  ];

  const dummyBlog3 = [
    { blogId: 3 },
    { blogDescription: "description3" },
    { blog_created_date: "2011-09-09" },
    { blog_terminated_date: "2011-12-09" },
    { status: "Active" },
    { accountId: 1 },
    { last_updated_date: "2011-11-09" }
  ];

  it("BlogService should be created", function(done){
    expect(blogService).toBeTruthy();
  });

  it("#getBlogs should return value from blog", function(done){
    (_done: DoneFn) => {
      const dummyBlogTest = [
        { blogId: 1 },
        { blogId: "title" },
        { blogDescription: "description" },
        { blog_created_date: "2011-09-09" },
        { blog_terminated_date: "2011-12-09" },
        { status: "Active" },
        { accountId: 1 },
        { last_updated_date: "2011-11-09" }
      ];

      const expectedBlog: BlogModel[] = [{
        blogId: 1,
        blogTitle: "title1",
        blogDescription: "description1",
        blog_created_date: "2011-09-09",
        blog_terminated_date: "2011-12-09",
        status: "Active",
        accountId: 1,
        last_updated_date: "2011-11-09"
      },{
        blogId: 2,
        blogTitle: "title2",
        blogDescription: "description2",
        blog_created_date: "2011-09-09",
        blog_terminated_date: "2011-12-09",
        status: "Active2",
        accountId: 1,
        last_updated_date: "2011-11-09"
      },{
        blogId: 3,
        blogTitle: "title3",
        blogDescription: "description3",
        blog_created_date: "2011-09-09",
        blog_terminated_date: "2011-12-09",
        status: "Active3",
        accountId: 1,
        last_updated_date: "2011-11-09"
      }];

      blogService.getBlogs().subscribe(blogData => {
        expect(blogData.length).toBe(3);
        expect(blogData[0].blogDescription).toEqual("description1");
        expect(blogData[1].blogDescription).toEqual("description2");
        expect(blogData[2].blogDescription).toEqual("description3");
      });

      const req = httpTestingController.expectOne(
        "http://localhost:9100/blogs"
      );
      expect(req.request.method).toEqual("GET");

      req.flush(expectedBlog);
    };
  });

  it("#getBlogs with Spy should return value from blog", function(done){
    (_done: DoneFn) => {
      const expectedBlog: BlogModel = {
        blogId: 1,
        blogTitle: "title",
        blogDescription: "description",
        blog_created_date: "2011-09-09",
        blog_terminated_date: "2011-12-09",
        status: "Active",
        accountId: 1,
        last_updated_date: "2011-11-09"
      };

      httpClientSpy.get.and.returnValue(of(expectedBlog));

      blogServiceSpy.getBlogs().subscribe(
        blogs => expect(blogs).toEqual(expectedBlog, 'expected heroes'),
        fail
      );

      expect(httpClientSpy.get.calls.count()).toBe(1, 'one call');
    };
  });

  it('should return an error when the server returns a 404', function(done){
    const errorResponse = new HttpErrorResponse({
      error: 'test 404 error',
      status: 404, statusText: 'Not Found'
    });
  
    httpClientSpy.get.and.returnValue(of(errorResponse));

    blogServiceSpy.getBlogs().subscribe(
      _blogs => fail('expected an error, not blogs'),
      error  => expect(error.message).toContain('test 404 error')
    );
  });

  it("#getBlogsById should return value from blog", function(done){
    blogService.getBlogsById(1).subscribe(blogData => {
      expect(blogData.blogDescription).toEqual("description");
    });

    const req = httpTestingController.expectOne(
      "http://localhost:9100/blogs/blog/1"
    );
    expect(req.request.method).toEqual("GET");
    req.flush(dummyBlog);
  });

  it("#getBlogPostsByBlogId should return value from blog", function(done){
    let dummyBlogPost = [
      { blogPostId: 1 },
      { blogId: 1 },
      { blogPostTitle: "blog post title" },
      { blogPostBody: "description blog post" },
      { blogPostCreatedDate: "2011-09-09" },
      { blogPostTerminatedDate: "2011-12-09" },
      { status: "Active" },
      { last_updated_date: "2011-11-09" }
    ];
    blogService.getBlogPostsByBlogId(1).subscribe(blogPostData => {
      expect(blogPostData.blogPostBody).toEqual("description blog post");
    });

    const req = httpTestingController.expectOne(
      "http://localhost:9100/blogs/blog/1/posts"
    );
    expect(req.request.method).toEqual("GET");
    req.flush(dummyBlogPost);
  });

  it("#addBlogPost should return value from blog", function(done){
    const expectedBlogPost: BlogModel = {
      blogId: 1,
      blogTitle: "title",
      blogDescription: "description",
      blog_created_date: "2011-09-09",
      blog_terminated_date: "2011-12-09",
      status: "Active",
      accountId: 1,
      last_updated_date: "2011-11-09"
    };

    blogService.addBlogPost(this.expectedBlogPost, 1).subscribe();

    const req = httpTestingController.expectOne(
      "http://localhost:9100/blogs/blog/post/1"
    );

    expect(req.request.method).toEqual("POST");
    req.flush(expectedBlogPost);
  });

  it("#saveBlogPost should return value from blog", function(_doneFn){
    const expectedBlogPost: BlogPostModel = {
      blogPostId: 1,
      blogId: 1,
      blogPostTitle: "blog post title",
      blogPostBody: "description blog post",
      blogPostCreatedDate: "2011-09-09",
      blogPostTerminatedDate: "2011-12-09",
      status: "Active",
      lastUpdateDate: "2011-11-09"
    };

    let dummyBlogPost1 = [
      { blogPostId: 1 },
      { blogId: 1 },
      { blogPostTitle: "blog post title" },
      { blogPostBody: "description blog post" },
      { blogPostCreatedDate: "2011-09-09" },
      { blogPostTerminatedDate: "2011-12-09" },
      { status: "Active" },
      { last_updated_date: "2011-11-09" }
    ];

    blogService.saveBlogPost(1, 1, expectedBlogPost).subscribe(blogData => {
      expect(blogData.blogPostDescription).toEqual("description blog post");
    });

    const req = httpTestingController.expectOne(
      "http://localhost:9100/blogs/blog/1/posts/post/edit/1"
    );
    expect(req.request.method).toEqual("PUT");
    req.flush(expectedBlogPost);
  });
});
