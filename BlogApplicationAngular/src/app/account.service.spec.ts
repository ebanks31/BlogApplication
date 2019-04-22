import { TestBed, async, getTestBed } from '@angular/core/testing';
import { AccountService } from './account.service';
import { HttpClientModule } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient } from '@angular/common/http';

describe('AccountService', () => {
  let injector: TestBed;
  let accountService: AccountService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [AccountService],
        imports: [HttpClientTestingModule]
      })
      .compileComponents();
      injector = getTestBed();
      accountService = injector.get(AccountService);
      httpMock = injector.get(HttpTestingController);
  });

  const dummyAccount = [
    { accountId: 1},
    { accountCreatedDate: '2011-09-09' },
    { accountTerminatedDate: '2011-09-09' },
    { status: 'Active' },
    { role: 'User' },
    { lastUpdatedDate: '2011-09-09' },
  ];

  //const req = httpMock.expectOne("http://localhost:4200/accounts");
 // expect(req.request.method).toBe("GET");

  const req = httpMock.expectOne({ method: 'GET', url: 'http://localhost:4200/accounts' });
  req.flush(dummyAccount);

  accountService.getAccounts().subscribe(users => {
    expect(users.length).toBe(1);
    expect(users).toEqual(dummyAccount);
  });

  it('should be created', () => {
    const service: AccountService = TestBed.get(AccountService);
    expect(service).toBeTruthy();
  });

  it('#getObservableValue should return value from observable',
    (done: DoneFn) => {
      accountService.getAccounts().subscribe(value => {
      expect(value).toBe('observable value');
      done();
    });
  });

  
afterEach(() => {
  httpMock.verify();
});
});
