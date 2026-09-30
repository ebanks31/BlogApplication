import { waitForAsync, ComponentFixture, TestBed } from "@angular/core/testing";

import { BlogPostComponent } from "./blogpost.component";
import { BlogService } from "../blog.service";
import { ActivatedRoute } from "@angular/router";
import { CKEditorModule } from "ckeditor4-angular";
import { of } from "rxjs";
import { FormsModule } from "@angular/forms";

describe("BlogPostComponent", () => {
  let component: BlogPostComponent;
  let fixture: ComponentFixture<BlogPostComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [FormsModule, CKEditorModule],
      declarations: [BlogPostComponent],
      providers: [
        { provide: BlogService, useValue: {
          getBlogPostByBlogId: jasmine.createSpy().and.returnValue(of({
            blogId: 9,
            blogPostId: 4,
            blogPostTitle: 'Title',
            blogPostBody: 'Body'
          })),
          saveBlogPost: jasmine.createSpy().and.returnValue(of({}))
        } },
        { provide: ActivatedRoute, useValue: { params: of({ blogId: 9, blogPostId: 4 }) } }
      ]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BlogPostComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });

  it('loads the post identified by the route', () => {
    const service = TestBed.inject(BlogService) as jasmine.SpyObj<BlogService>;

    expect(service.getBlogPostByBlogId).toHaveBeenCalledWith(9, 4);
    expect(component.blogPost.blogPostTitle).toBe('Title');
  });

  it('toggles editing and saves the current post', () => {
    const service = TestBed.inject(BlogService) as jasmine.SpyObj<BlogService>;

    component.editPost();
    expect(component.showTextFormattingToolbar).toBeTrue();
    expect(component.saveButtonClick).toBeFalse();

    component.savePost();
    expect(component.showTextFormattingToolbar).toBeFalse();
    expect(component.saveButtonClick).toBeTrue();
    expect(service.saveBlogPost).toHaveBeenCalledWith(9, 4, component.blogPost);
  });
});
