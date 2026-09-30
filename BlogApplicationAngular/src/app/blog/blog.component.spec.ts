import { waitForAsync, ComponentFixture, TestBed } from '@angular/core/testing';

import { BlogComponent } from './blog.component';
import { BlogService } from '../blog.service';
import { ActivatedRoute, Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { FormsModule } from '@angular/forms';
import { of } from 'rxjs';

describe('BlogComponent', () => {
  let component: BlogComponent;
  let fixture: ComponentFixture<BlogComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({imports: [
      RouterTestingModule, FormsModule
  ],
      declarations: [ BlogComponent ],
      providers: [
        { provide: BlogService, useValue: {
          getBlogsById: jasmine.createSpy().and.returnValue(of({ blogId: 7 })),
          getBlogPostsByBlogId: jasmine.createSpy().and.returnValue(of([])),
          deleteBlogPost: jasmine.createSpy().and.returnValue(of({}))
        } },
        { provide: ActivatedRoute, useValue: { params: of({ id: 7 }) } },
        { provide: Router, useValue: { navigate: jasmine.createSpy() } }
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BlogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should display original title', () => {
    expect(component.color).toBe("light blue");
  });

  it('loads the blog and posts for the route id', () => {
    const service = TestBed.inject(BlogService) as jasmine.SpyObj<BlogService>;

    expect(component.blogId).toBe(7);
    expect(component.blog.blogId).toBe(7);
    expect(service.getBlogsById).toHaveBeenCalledWith(7);
    expect(service.getBlogPostsByBlogId).toHaveBeenCalledWith(7);
  });

  it('navigates to the add, edit, and deleted-post destinations', () => {
    const router = TestBed.inject(Router);
    const service = TestBed.inject(BlogService) as jasmine.SpyObj<BlogService>;

    component.addBlogPost();
    expect(router.navigate).toHaveBeenCalledWith(['blogs/blog/7/posts/post/add']);

    component.editBlogPost(4);
    expect(router.navigate).toHaveBeenCalledWith(['blogs/blog/7/posts/4']);

    component.deleteBlogPost(4);
    expect(service.deleteBlogPost).toHaveBeenCalledWith(4, 7);
    expect(router.navigate).toHaveBeenCalledWith(['blog/7']);
  });
});
