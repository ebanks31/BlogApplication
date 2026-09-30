import { waitForAsync, ComponentFixture, TestBed } from "@angular/core/testing";


import { DeveloperComponent } from "./developer.component";

describe("DeveloperComponent", () => {
  let component: DeveloperComponent;
  let fixture: ComponentFixture<DeveloperComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [DeveloperComponent]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DeveloperComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });

  it('renders the developer page message', () => {
    expect(fixture.nativeElement.textContent).toContain('developer works!');
  });
});
