import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { BlogComponent } from './blog.component';
import { HttpClientModule } from '@angular/common/http';
import { RouterTestingModule } from '@angular/router/testing';
import { FormsModule } from '@angular/forms';

describe('BlogComponent', () => {
  let component: BlogComponent;
  let fixture: ComponentFixture<BlogComponent>;
  let h1:        HTMLElement;

  beforeEach(async(() => {
    TestBed.configureTestingModule({imports: [
      HttpClientModule, RouterTestingModule, FormsModule    
  ],
      declarations: [ BlogComponent ]
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
    expect(h1.textContent).toContain(component.color);
  });
});
