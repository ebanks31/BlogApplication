import { TestBed, getTestBed } from '@angular/core/testing';
import { UserService } from './user.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { UserModel } from './user.component';

describe('UserService', () => {
  let userService: UserService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [UserService],
        imports: [HttpClientTestingModule]
      })
      .compileComponents();

    // We inject our service (which imports the HttpClient) and the Test Controller
    httpTestingController = TestBed.get(HttpTestingController);
    userService = TestBed.get(UserService);
  });

  const expectedUser: UserModel = {
    userId: 1,
    firstname: "John",
    lastname: "Doe",
    middlename: "Fred",
    lastUpdatedDate: "2011-12-09"
  };

  it('should be created', () => {
    const service: UserService = TestBed.get(UserService);
    expect(service).toBeTruthy();
  });

  
  it("#getUsers should return value from users", () => {
    (_done: DoneFn) => {

      
  const expectedUserList: UserModel[] = [{
    userId: 1,
    firstname: "John",
    lastname: "Doe",
    middlename: "Fred",
    lastUpdatedDate: "2011-12-09"
  },{
    userId: 2,
    firstname: "John1",
    lastname: "Doe1",
    middlename: "Fred1",
    lastUpdatedDate: "2011-12-10"
  },{
    userId: 3,
    firstname: "John2",
    lastname: "Doe2",
    middlename: "Fred2",
    lastUpdatedDate: "2011-12-11"
  }];


      userService.getUsers().subscribe(userData => {
        expect(userData.length).toBe(3);
        expect(userData[0].firstname).toEqual("John");
        expect(userData[1].firstname).toEqual("John1");
        expect(userData[2].firstname).toEqual("John2");
      });

      const req = httpTestingController.expectOne(
        "http://localhost:9100/users"
      );
      expect(req.request.method).toEqual("GET");

      req.flush(expectedUserList);
    };
  });

  it("#getUsersById should return value from user by Id", () => {
    (_done: DoneFn) => {

      userService.getUserById(1).subscribe(userData => {
        expect(userData.firstname).toEqual("John");
      });

      const req = httpTestingController.expectOne(
        "http://localhost:9100/users/1"
      );
      expect(req.request.method).toEqual("GET");

      req.flush(expectedUser);
    };
  });

  it("#addUser should return value from users", () => {
    (_done: DoneFn) => {

      userService.addUser(expectedUser).subscribe(userData => {});

      const req = httpTestingController.expectOne(
        "http://localhost:9100/users/user/add"
      );
      expect(req.request.method).toEqual("POST");

      req.flush(expectedUser);
    };
  });

  it("#editUser should return value from users", () => {
    (_done: DoneFn) => {

      userService.editUser(1, expectedUser).subscribe(userData => {
        expect(userData.length).toBe(1);
        expect(userData.firstname).toEqual("John");
      });

      const req = httpTestingController.expectOne(
        "http://localhost:9100/users/users/edit/1"
      );
      expect(req.request.method).toEqual("PUT");

      req.flush(expectedUser);
    };
  });

  it("#deleteUser should return value from users", () => {
    (_done: DoneFn) => {

      userService.deleteUser(1).subscribe(userData => {});

      const req = httpTestingController.expectOne(
        "http://localhost:9100/users/users/delete/1"
      );
      expect(req.request.method).toEqual("DELETE");

      req.flush(expectedUser);
    };
  });
});