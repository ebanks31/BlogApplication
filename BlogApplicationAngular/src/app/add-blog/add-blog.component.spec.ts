import { waitForAsync, ComponentFixture, TestBed } from "@angular/core/testing";

import { AddBlogComponent } from "./add-blog.component";
import { BlogService } from "../blog.service";
import { FormsModule } from "@angular/forms";
import { RouterTestingModule } from "@angular/router/testing";
import { Router } from "@angular/router";
import { of } from "rxjs";

describe("AddBlogComponent", () => {
  let component: AddBlogComponent;
  let fixture: ComponentFixture<AddBlogComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [FormsModule, RouterTestingModule],
      declarations: [AddBlogComponent],
      providers: [
        { provide: BlogService, useValue: { addBlog: jasmine.createSpy().and.returnValue(of({})) } },
        { provide: Router, useValue: { navigate: jasmine.createSpy() } }
      ]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddBlogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });

  it('submits the entered blog and returns to the blog list', () => {
    component.blogTitle = 'Testing';
    component.blogDescription = 'A test blog';

    component.addBlog();

    expect(TestBed.inject(BlogService).addBlog).toHaveBeenCalledWith(
      jasmine.objectContaining({ blogTitle: 'Testing', blogDescription: 'A test blog' })
    );
    expect(TestBed.inject(Router).navigate).toHaveBeenCalledWith(['blogs']);
  });
});
