import { Component, OnInit } from '@angular/core';
import { BlogService } from '../blog.service';
import { BlogModel } from '../blog.component';
import { Router } from '@angular/router';

/**
 * Component
 */
@Component({
  selector: 'app-blogs',
  templateUrl: './blogs.component.html',
  styleUrls: ['./blogs.component.css']
})
export class BlogsComponent implements OnInit {

  constructor(private blogService: BlogService, private router: Router) { }
  blogs: BlogModel[];

  /**
   * on init
   */
  ngOnInit() {
    this.getBlogs();
  }
  
  /**
   * Gets blogs
   */
  getBlogs(): void {
    this.blogService.getBlogs()
      .subscribe(data => this.blogs = data,
        error => console.log(error));
  }
}