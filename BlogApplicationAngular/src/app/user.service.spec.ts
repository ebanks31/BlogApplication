import { TestBed, getTestBed } from '@angular/core/testing';
import { UserService } from './user.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

describe('UserService', () => {
  let userService: UserService;
  let injector: TestBed;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [UserService],
        imports: [HttpClientTestingModule]
      })
      .compileComponents();
      injector = getTestBed();
      userService = injector.get(userService);
      httpMock = injector.get(HttpTestingController);

      const req = httpMock.expectOne({ method: 'GET', url: 'http://localhost:9100/users' });
      req.flush(dummyUsers);
    
      userService.getUsers().subscribe(blogs => {
        expect(blogs.length).toBe(1);
        expect(blogs).toEqual(dummyUsers);
      });
  });

  const dummyUsers = [
    { userId: 1},
    { firstname: 'John' },
    { lastname: 'Doe' }
  ];

  it('should be created', () => {
    const service: UserService = TestBed.get(UserService);
    expect(service).toBeTruthy();
  });
});
