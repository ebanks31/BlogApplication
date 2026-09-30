import { waitForAsync, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddBlogPostComponent } from './add-blog-post.component';
import { BlogService } from '../blog.service';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { of } from 'rxjs';

describe('AddBlogPostComponent', () => {
  let component: AddBlogPostComponent;
  let fixture: ComponentFixture<AddBlogPostComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [FormsModule],
      declarations: [AddBlogPostComponent],
      providers: [
        { provide: BlogService, useValue: { addBlogPost: jasmine.createSpy().and.returnValue(of({})) } },
        { provide: Router, useValue: { navigate: jasmine.createSpy() } },
        { provide: ActivatedRoute, useValue: { params: of({ blogId: 12 }) } }
      ]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddBlogPostComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('submits a post for the current blog and navigates back to it', () => {
    component.blogTitle = 'Post title';
    component.blogPostBody = 'Post body';

    component.addBlogPost();

    expect(TestBed.inject(BlogService).addBlogPost).toHaveBeenCalledWith(
      jasmine.objectContaining({ blogPostTitle: 'Post title', blogPostBody: 'Post body' }),
      12
    );
    expect(TestBed.inject(Router).navigate).toHaveBeenCalledWith(['blogs/blog/12']);
  });
});
