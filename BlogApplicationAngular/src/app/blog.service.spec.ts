import { BlogModel } from "./blog.component";
import { BlogPostModel } from "./blogpost.component";
import { TestBed, getTestBed } from "@angular/core/testing";
import { BlogService } from "./blog.service";
import {
  HttpClientTestingModule,
  HttpTestingController
} from "@angular/common/http/testing";


describe("BlogService", () => {
  let blogService: BlogService;
  let httpTestingController: HttpTestingController;
  
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [BlogService],
      imports: [HttpClientTestingModule]
    });

    // We inject our service (which imports the HttpClient) and the Test Controller
    httpTestingController = TestBed.get(HttpTestingController);
    blogService = TestBed.get(BlogService);
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

  it("BlogService should be created", () => {
    expect(blogService).toBeTruthy();
  });

  it("#getBlogs should return value from blog", () => {
    (done: DoneFn) => {
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

      blogService.getBlogs().subscribe(blogData => {
        expect(blogData.blogDescription).toEqual("title");
      });

      const req = httpTestingController.expectOne(
        "http://localhost:9100/blogs"
      );
      expect(req.request.method).toEqual("GET");

      req.flush(dummyBlogTest);
    };
  });

  it("#getBlogsById should return value from blog", (done: DoneFn) => {
    blogService.getBlogsById(1).subscribe(blogData => {
      expect(blogData.blogDescription).toEqual("description");
    });

    const req = httpTestingController.expectOne("http://localhost:9100/blogs/blog/1");
    expect(req.request.method).toEqual("GET");
    req.flush(dummyBlog);
  });

  it("#getBlogPostsByBlogId should return value from blog", (done: DoneFn) => {
    blogService.getBlogPostsByBlogId(1).subscribe(blogPostData => {
      expect(blogPostData.blogPostDescription).toEqual( "description blog post");
    });

    const req = httpTestingController.expectOne("http://localhost:9100/blogs/blog/1/posts");
    expect(req.request.method).toEqual("GET");
    req.flush(dummyBlog);
  });

  it("#addBlogPost should return value from blog", (done: DoneFn) => {
    blogService.addBlogPost(this.dummyBlog, 1).subscribe();

    const req = httpTestingController.expectOne("http://localhost:9100/blogs/blog/post/1");
    expect(req.request.method).toEqual("POST");
    req.flush(dummyBlog);
  });

  it("#saveBlogPost should return value from blog", (done: DoneFn) => {
    const expectedBlogPost: BlogPostModel[] =
    [
      { blogPostId: 1 ,
      blogId: 1 ,
       blogPostTitle: "blog post title" ,
       blogPostBody: "description blog post",
       blogPostCreatedDate: "2011-09-09" ,
       blogPostTerminatedDate: "2011-12-09" ,
       status: "Active",
       lastUpdateDate: "2011-11-09" }
    ];

    let dummyBlogPost1 = [
      { blogPostId: 1 },
      { blogId: 1 },
      { blogPostTitle: "blog post title" },
      { blogPostBody: "description blog post"},
      { blogPostCreatedDate: "2011-09-09" },
      { blogPostTerminatedDate: "2011-12-09" },
      { status: "Active" },
      { last_updated_date: "2011-11-09" }
    ];
  
    blogService.saveBlogPost(1,1,expectedBlogPost[0]).subscribe(blogData => {
      expect(blogData.blogPostDescription).toEqual( "description blog post");
    });

    const req = httpTestingController.expectOne("http://localhost:9100/blogs/blog/1/posts/post/edit/1");
    expect(req.request.method).toEqual("PUT");
    req.flush(dummyBlogPost1);
  });
});
