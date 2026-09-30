import { BlogModel } from './../blog.component';
import { waitForAsync, ComponentFixture, TestBed } from "@angular/core/testing";

import { BlogsComponent } from "./blogs.component";
import { BlogService } from "../blog.service";
import { RouterTestingModule } from "@angular/router/testing";
import { FormsModule } from "@angular/forms";
import { of } from "rxjs";

describe("BlogsComponent", () => {
  let component: BlogsComponent;
  let fixture: ComponentFixture<BlogsComponent>;
  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule, FormsModule],
      declarations: [BlogsComponent],
      providers: [
        { provide: BlogService, useValue: { getBlogs: () => of([{
          blogId: 1,
          blogTitle: 'blogTitle',
          blogDescription: 'description',
          blog_created_date: '2011-09-09',
          blog_terminated_date: '2011-12-09',
          status: 'Active',
          accountId: 1,
          last_updated_date: '2011-11-09'
        }]) } }
      ]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BlogsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });

  it('should have <h2> with "banner works!"', () => {
    const bannerElement: HTMLElement = fixture.nativeElement;
    const p = bannerElement.querySelector('h2');
    expect(p?.textContent).toEqual('Blogs');
  });

  it('should have <blogs> with "banner works!"', () => {
    const expectedBlog: BlogModel[] =
    [{
       blogId: 1 ,
       blogTitle: "blogTitle" ,
       blogDescription: "description" ,
       blog_created_date: "2011-09-09" ,
       blog_terminated_date: "2011-12-09" ,
       status: "Active" ,
       accountId: 1 ,
       last_updated_date: "2011-11-09" }
    ];

    component.blogs = expectedBlog;
    fixture.detectChanges();
    const bannerElement: HTMLElement = fixture.nativeElement;
    const div = bannerElement.querySelector('.blogs');
    expect(bannerElement.textContent).toContain('Your Blogs');

    expect(div?.querySelector('a')?.textContent).toContain(expectedBlog[0].blogTitle);
  });
});