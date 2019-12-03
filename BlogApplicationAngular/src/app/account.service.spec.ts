import { TestBed, getTestBed } from "@angular/core/testing";
import { AccountService } from "./account.service";
import {
  HttpClientTestingModule,
  HttpTestingController
} from "@angular/common/http/testing";

describe("AccountService", () => {
  let injector: TestBed;
  let accountService: AccountService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [AccountService],
      imports: [HttpClientTestingModule]
    }).compileComponents();
    injector = getTestBed();
    accountService = injector.get(AccountService);
    // We inject our service (which imports the HttpClient) and the Test Controller
    httpTestingController = TestBed.get(HttpTestingController);
    accountService = TestBed.get(AccountService);

    // const req = httpMock.expectOne({ method: 'GET', url: 'http://localhost:9100/accounts' });
    // req.flush(dummyAccount);
    /*
      accountService.getAccounts().subscribe(users => {
        expect(users.length).toBe(1);
        expect(users).toEqual(dummyAccount);
      });*/
  });

  const dummyAccount = [
    { accountId: 1 },
    { accountCreatedDate: "2011-09-09" },
    { accountTerminatedDate: "2011-09-09" },
    { status: "Active" },
    { role: "User" },
    { lastUpdatedDate: "2011-09-09" }
  ];

  it("should be created", () => {
    expect(accountService).toBeTruthy();
  });

  it("#getAccounts should return value from acconts", () => {
    (_done: DoneFn) => {
      accountService.getAccounts().subscribe(accountData => {
        expect(accountData.status).toEqual("Active");
      });

      const req = httpTestingController.expectOne(
        "http://localhost:9100/accounts"
      );
      expect(req.request.method).toEqual("GET");

      req.flush(dummyAccount);
    };
  });

  afterEach(() => {
    httpTestingController.verify();
  });
});
