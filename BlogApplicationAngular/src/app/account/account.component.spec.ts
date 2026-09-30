import { waitForAsync, ComponentFixture, TestBed } from "@angular/core/testing";

import { AccountComponent } from "./account.component";
import { AccountService } from "../account.service";
import { UserService } from "../user.service";
import { ActivatedRoute } from "@angular/router";
import { of } from "rxjs";
import { FormsModule } from "@angular/forms";
import { RouterTestingModule } from "@angular/router/testing";

describe("AccountComponent", () => {
  let component: AccountComponent;
  let fixture: ComponentFixture<AccountComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [FormsModule, RouterTestingModule],
      declarations: [AccountComponent],
      providers: [
        { provide: AccountService, useValue: { getAccountsById: () => of({ accountId: 7 }) } },
        { provide: UserService, useValue: { getUserById: () => of({ userId: 3 }) } },
        { provide: ActivatedRoute, useValue: { params: of({ id: 7 }) } }
      ]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AccountComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });

  it('loads route-specific account and user data', () => {
    expect(component.id).toBe(7);
    expect(component.account.accountId).toBe(7);
    expect(component.user.userId).toBe(3);
  });
});
