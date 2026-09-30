import { waitForAsync, ComponentFixture, TestBed } from "@angular/core/testing";


import { ContactComponent } from "./contact.component";

describe("ContactComponent", () => {
  let component: ContactComponent;
  let fixture: ComponentFixture<ContactComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ContactComponent]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ContactComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });

  it('initializes the editor configuration and content', () => {
    expect(component.mycontent).toContain('My html content');
    expect(component.ckeConfig).toEqual(jasmine.objectContaining({
      allowedContent: false,
      forcePasteAsPlainText: true,
      toolbar_Basic: ['Bold', 'Italic']
    }));
  });
});
