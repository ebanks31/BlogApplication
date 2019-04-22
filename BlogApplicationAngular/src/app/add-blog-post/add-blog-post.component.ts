import { Component, OnInit } from '@angular/core';
import { BlogService } from '../blog.service';
import { BlogPostModel } from '../blogpost.component';
import { Router, ActivatedRoute } from '@angular/router';

/**
 * Component
 */
@Component({
  selector: 'app-add-blog-post',
  templateUrl: './add-blog-post.component.html',
  styleUrls: ['./add-blog-post.component.css']
})
export class AddBlogPostComponent implements OnInit {
  blogPost: BlogPostModel;
  blogTitle: string;
  blogPostBody: string;
  blogId: number;

  /**
   * Creates an instance of add blog post component.
   * @param blogService 
   * @param router 
   * @param route 
   */
  constructor(private blogService: BlogService, private router: Router,
    private route: ActivatedRoute) {
    this.route.params.subscribe(params => this.blogId = params.blogId,
      error => console.log(error))
  }

  /**
   * on init
   */
  ngOnInit() {
  }

  /**
   * Adds blog post
   * @param $event 
   */
  addBlogPost($event: any): void {
    console.log("addBlogPost()");

    console.log("this.blogTitle: " + this.blogTitle);
    console.log("this.blogDescription: " + this.blogPostBody);
    this.blogPost = new BlogPostModel();
    this.blogPost.blogPostTitle = this.blogTitle;
    this.blogPost.blogPostBody = this.blogPostBody;
    console.log("this.blogPost.blogPostTitle: " + this.blogPost.blogPostTitle);
    console.log("this.blogPost.blogPostBody: " + this.blogPost.blogPostBody);

    this.blogService.addBlogPost(this.blogPost, this.blogId).subscribe(data => console.log(data),
      error => console.log(error));
    console.log("addBlog");
    console.log("this.blogId: " + this.blogId);

    this.router.navigate(['blogs/blog/' + this.blogId]);
  }
}
