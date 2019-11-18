import { Component, OnInit } from "@angular/core";
import { BlogService } from "../blog.service";
import { BlogModel } from "../blog.component";
import { Router } from "@angular/router";

/**
 * Component
 */
@Component({
  selector: "app-add-blog",
  templateUrl: "./add-blog.component.html",
  styleUrls: ["./add-blog.component.css"]
})
export class AddBlogComponent implements OnInit {
  blogId: number;
  blog: BlogModel;
  blogTitle: string;
  blogDescription: string;

  /**
   * Creates an instance of add blog component.
   * @param blogService
   * @param router
   */
  constructor(private blogService: BlogService, private router: Router) {}

  /**
   * on init
   */
  ngOnInit() {}

  /**
   * Adds a blog
   * @param $event
   */
  addBlog($event: any): void {
    console.log("this.blogTitle: " + this.blogTitle);
    console.log("this.blogDescription: " + this.blogDescription);
    this.blog = new BlogModel();
    this.blog.blogTitle = this.blogTitle;
    this.blog.blogDescription = this.blogDescription;
    console.log("this.blog.blogTitle: " + this.blog.blogTitle);
    console.log("this.blog.blogDescription: " + this.blog.blogDescription);

    this.blogService.addBlog(this.blog).subscribe(
      data => console.log(data),
      error => console.log(error)
    );
    console.log("addBlog");

    this.router.navigate(["blogs"]);
  }
}
