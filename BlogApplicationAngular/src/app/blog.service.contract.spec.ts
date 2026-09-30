import { TestBed } from "@angular/core/testing";
import {
  HttpClientTestingModule,
  HttpTestingController
} from "@angular/common/http/testing";
import { BlogModel } from "./blog.component";
import { BlogPostModel } from "./blogpost.component";
import { BlogService } from "./blog.service";

describe("BlogService HTTP contract", () => {
  let service: BlogService;
  let http: HttpTestingController;

  const blog: BlogModel = {
    blogId: 2,
    blogTitle: "Unit testing",
    blogDescription: "A sample blog",
    blog_created_date: "2026-01-01",
    blog_terminated_date: "2026-12-31",
    status: "Active",
    accountId: 5,
    last_updated_date: "2026-01-01"
  };
  const post: BlogPostModel = {
    blogPostId: 4,
    blogId: 2,
    blogPostCreatedDate: "2026-01-01",
    blogPostTerminatedDate: "2026-12-31",
    lastUpdateDate: "2026-01-01",
    status: "Active",
    blogPostBody: "A sample post",
    blogPostTitle: "Post title"
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [BlogService]
    });
    service = TestBed.inject(BlogService);
    http = TestBed.inject(HttpTestingController);
  });

  afterEach(() => http.verify());

  it("gets all blogs", () => {
    service.getBlogs().subscribe(result => expect(result).toEqual([blog]));
    const request = http.expectOne("http://localhost:9600/blogs");
    expect(request.request.method).toBe("GET");
    request.flush([blog]);
  });

  it("gets a blog by id", () => {
    service.getBlogsById(2).subscribe(result => expect(result).toEqual(blog));
    const request = http.expectOne("http://localhost:9600/blogs/blog/2");
    expect(request.request.method).toBe("GET");
    request.flush(blog);
  });

  it("gets posts for a blog", () => {
    service.getBlogPostsByBlogId(2).subscribe(result => expect(result).toEqual([post]));
    const request = http.expectOne("http://localhost:9600/blogs/blog/2/posts");
    expect(request.request.method).toBe("GET");
    request.flush([post]);
  });

  it("gets one post by blog and post id", () => {
    service.getBlogPostByBlogId(2, 4).subscribe(result => expect(result).toEqual(post));
    const request = http.expectOne("http://localhost:9600/blogs/blog/2/posts/4");
    expect(request.request.method).toBe("GET");
    request.flush(post);
  });

  it("adds a blog", () => {
    service.addBlog(blog).subscribe(result => expect(result).toEqual(blog));
    const request = http.expectOne("http://localhost:9600/blogs/blog/add");
    expect(request.request.method).toBe("POST");
    expect(request.request.body).toBe(JSON.stringify(blog));
    request.flush(blog);
  });

  it("saves a blog", () => {
    service.saveBlog(JSON.stringify(blog)).subscribe(result => expect(result).toEqual(blog));
    const request = http.expectOne("http://localhost:9600/blogs");
    expect(request.request.method).toBe("POST");
    expect(request.request.body).toBe(JSON.stringify(blog));
    request.flush(blog);
  });

  it("adds a post to a blog", () => {
    service.addBlogPost(post, 2).subscribe(result => expect(result).toEqual(post));
    const request = http.expectOne("http://localhost:9600/blogs/blog/2/posts/post/add");
    expect(request.request.method).toBe("POST");
    expect(request.request.body).toBe(JSON.stringify(post));
    request.flush(post);
  });

  it("updates a blog post", () => {
    service.saveBlogPost(2, 4, post).subscribe(result => expect(result).toEqual(post));
    const request = http.expectOne("http://localhost:9600/blogs/blog/2/posts/post/edit/4");
    expect(request.request.method).toBe("PUT");
    expect(request.request.body).toBe(JSON.stringify(post));
    request.flush(post);
  });

  it("deletes a blog post", () => {
    service.deleteBlogPost(4, 2).subscribe(result => expect(result).toEqual({ deleted: true }));
    const request = http.expectOne("http://localhost:9600/blogs/blog/2/posts/post/delete/4");
    expect(request.request.method).toBe("DELETE");
    request.flush({ deleted: true });
  });

  it("propagates HTTP errors", () => {
    let responseStatus = 0;
    service.getBlogs().subscribe({
      error: error => (responseStatus = error.status)
    });
    http.expectOne("http://localhost:9600/blogs").flush("not found", {
      status: 404,
      statusText: "Not Found"
    });
    expect(responseStatus).toBe(404);
  });
});
