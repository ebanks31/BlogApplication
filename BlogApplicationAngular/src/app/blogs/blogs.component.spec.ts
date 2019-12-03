import { BlogModel } from './../blog.component';
import { async, ComponentFixture, TestBed } from "@angular/core/testing";
import { BlogsComponent } from "./blogs.component";
import { RouterTestingModule } from "@angular/router/testing";
import { HttpClientModule } from "@angular/common/http";
import { FormsModule } from "@angular/forms";

describe("BlogsComponent", () => {
  let component: BlogsComponent;
  let fixture: ComponentFixture<BlogsComponent>;
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule, HttpClientModule, FormsModule],
      declarations: [BlogsComponent]
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
    expect(p.textContent).toEqual('Blogs');
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

    component.blogs=BlogModel[0];
    fixture.detectChanges();
    const bannerElement: HTMLElement = fixture.nativeElement;
    const div = bannerElement.querySelector('blogs');
    expect(div.innerHTML).toEqual('Your Blogs');

    const expectedPipedName = expectedBlog[0].blogTitle.toUpperCase();
    expect(div.textContent).toContain(expectedPipedName);
  });
});