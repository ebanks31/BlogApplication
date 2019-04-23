import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { BlogPostModel } from './blogpost.component';

/**
 * Injectable
 */
@Injectable({
  providedIn: 'root'
})
export class BlogService {

  blogsUrl = "http://localhost:9100/blogs";
  blogUrl = "http://localhost:9100/blogs/blog/";
  blogPostsByBlogIdUrl = "/posts";
  blogPostByBlogIdUrl = "/posts/";
  post = "/post/";
  edit = "edit/";
  add = "add";
  headers = new HttpHeaders({
    'Content-Type': 'application/json'
  });

  constructor(private http: HttpClient) {
  }

  /**
   * Adds blog
   * @param blog 
   * @returns  
   */
  addBlog(blog: any) {
    console.log("addBlog ");

    let blogPostsByBlogIdUrlFinal = this.blogUrl.concat(this.add);
    console.log("blogPostsByBlogIdUrlFinal " + blogPostsByBlogIdUrlFinal);

    return this.http.post(blogPostsByBlogIdUrlFinal, JSON.stringify(blog), { headers: this.headers, responseType: 'json' });
  }

  saveBlog(user: string): Observable<any> {
    return this.http.post(this.blogsUrl, user, { responseType: 'json' });
  }

  getBlogs(): Observable<any> {
    return this.http.get(this.blogsUrl, { responseType: 'json' });
  }

  getBlogsById(id: number): Observable<any> {
    let finalBlogUrl = this.blogUrl + String(id);
    console.log("finalBlogUrl: " + finalBlogUrl)

    return this.http.get(finalBlogUrl, { responseType: 'json' });
  }

  getBlogPostsByBlogId(blogId: number): Observable<any> {
    console.log("getBlogsPostsByBlogId()")

    console.log("blogId: " + blogId)
    console.log("this.blogUrl: " + this.blogUrl)
    console.log("this.blogPostsByBlogIdUrl: " + this.blogPostsByBlogIdUrl + blogId)
    console.log("this.this.blogUrl : " + this.blogUrl + String(blogId))

    let blogPostsByBlogIdUrlFinal = this.blogUrl.concat(String(blogId)).concat(this.blogPostsByBlogIdUrl);
    console.log("this.blogPostsByBlogIdUrl final: " + blogPostsByBlogIdUrlFinal)

    return this.http.get(blogPostsByBlogIdUrlFinal, { responseType: 'json' });
  }
  /**
   * Gets blog post by blog id
   * @param blogId 
   * @param blogPostId 
   * @returns blog post by blog id 
   */
  getBlogPostByBlogId(blogId: number, blogPostId: number): Observable<any> {
    console.log("getBlogsPostByBlogId()")

    console.log("blogId: " + blogId)
    console.log("blogPostId: " + blogPostId)

    let blogUrlFinal = this.blogUrl + blogId + this.blogPostByBlogIdUrl + blogPostId;
    console.log("finalBlogUrl: " + blogUrlFinal)
    return this.http.get(blogUrlFinal, { responseType: 'json' });
  }
  /**
   * Adds blog post
   * @param blogPost 
   * @param blogId 
   */
  addBlogPost(blogPost: BlogPostModel, blogId: number) {
    console.log("addBlog ");

    let addBlogPostUrl = this.blogUrl.concat(String(blogId)).concat(this.blogPostByBlogIdUrl).concat("post/").concat(this.add);
    console.log("addBlogPostUrl " + addBlogPostUrl);

    return this.http.post(addBlogPostUrl, JSON.stringify(blogPost), { headers: this.headers, responseType: 'json' });
  }
  /**
   * Saves blog post
   * @param blogId 
   * @param blogPostId 
   * @param blogPost 
   * @returns blog post 
   */
  saveBlogPost(blogId: number, blogPostId: number, blogPost: BlogPostModel): Observable<any> {
    console.log("saveBlogPost");

    let saveBlogPostUrl = this.blogUrl.concat(String(blogId)).concat(this.blogPostByBlogIdUrl).concat(this.post).concat(this.edit).concat(String(blogPostId));
    console.log("saveBlogPostUrl " + saveBlogPostUrl);
    console.log("blogPost " + blogPost);
    console.log("JSON.stringify({blogPost} " + JSON.stringify(blogPost));

    return this.http.put(saveBlogPostUrl, JSON.stringify(blogPost), { headers: this.headers, responseType: 'json' });
  }
}