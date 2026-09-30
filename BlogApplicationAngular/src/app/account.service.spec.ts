import { TestBed } from "@angular/core/testing";
import { AccountService } from "./account.service";
import {
  HttpClientTestingModule,
  HttpTestingController
} from "@angular/common/http/testing";

describe("AccountService", () => {
  let accountService: AccountService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [AccountService],
      imports: [HttpClientTestingModule]
    }).compileComponents();
    // We inject our service (which imports the HttpClient) and the Test Controller
    httpTestingController = TestBed.inject(HttpTestingController);
    accountService = TestBed.inject(AccountService);

    // const req = httpMock.expectOne({ method: 'GET', url: 'http://localhost:9100/accounts' });
    // req.flush(dummyAccount);
    /*
      accountService.getAccounts().subscribe(users => {
        expect(users.length).toBe(1);
        expect(users).toEqual(dummyAccount);
      });*/
  });

  it("should be created", () => {
    expect(accountService).toBeTruthy();
  });

  it("gets the account list", () => {
    const response = [{ accountId: 1, status: "Active" }];

    accountService.getAccounts().subscribe(accounts => {
      expect(accounts).toEqual(response);
    });

    const request = httpTestingController.expectOne("http://localhost:9600/accounts");
    expect(request.request.method).toBe("GET");
    request.flush(response);
  });

  it("gets one account by id", () => {
    const response = { accountId: 7, status: "Active" };

    accountService.getAccountsById(7).subscribe(account => {
      expect(account).toEqual(response);
    });

    const request = httpTestingController.expectOne("http://localhost:9600/accounts/7");
    expect(request.request.method).toBe("GET");
    request.flush(response);
  });

  afterEach(() => {
    httpTestingController.verify();
  });
});
