import { waitForAsync, ComponentFixture, TestBed } from '@angular/core/testing';

import { AccountsComponent } from './accounts.component';
import { AccountService } from '../account.service';
import { of } from 'rxjs';
import { FormsModule } from '@angular/forms';

describe('AccountsComponent', () => {
  let component: AccountsComponent;
  let fixture: ComponentFixture<AccountsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({imports: [
        FormsModule
  ],
        declarations: [ AccountsComponent ],
        providers: [{ provide: AccountService, useValue: { getAccounts: () => of([]) } }]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AccountsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('loads accounts during initialization', () => {
    expect(component.accounts).toEqual([]);
  });

  it('parses account JSON', () => {
    expect(component.ConvertToJSON('{"accountId":7}')).toEqual({ accountId: 7 });
  });
});
