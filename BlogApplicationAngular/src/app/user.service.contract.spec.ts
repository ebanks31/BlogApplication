import { TestBed } from "@angular/core/testing";
import {
  HttpClientTestingModule,
  HttpTestingController
} from "@angular/common/http/testing";
import { UserModel } from "./user.component";
import { UserService } from "./user.service";

describe("UserService HTTP contract", () => {
  let service: UserService;
  let http: HttpTestingController;

  const user: UserModel = {
    userId: 3,
    firstname: "Ada",
    lastname: "Lovelace",
    middlename: "Byron",
    lastUpdatedDate: "2026-01-01"
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [UserService]
    });
    service = TestBed.inject(UserService);
    http = TestBed.inject(HttpTestingController);
  });

  afterEach(() => http.verify());

  it("gets all users", () => {
    service.getUsers().subscribe(result => expect(result).toEqual([user]));
    const request = http.expectOne("http://localhost:9600/users");
    expect(request.request.method).toBe("GET");
    request.flush([user]);
  });

  it("gets a user by id", () => {
    service.getUserById(3).subscribe(result => expect(result).toEqual(user));
    const request = http.expectOne("http://localhost:9600/users/3");
    expect(request.request.method).toBe("GET");
    request.flush(user);
  });

  it("adds a user with the supplied payload", () => {
    service.addUser(user).subscribe(result => expect(result).toEqual(user));
    const request = http.expectOne("http://localhost:9600/users//user//edit/add");
    expect(request.request.method).toBe("POST");
    expect(request.request.body).toEqual(user);
    request.flush(user);
  });

  it("saves a user", () => {
    service.saveUser(user).subscribe(result => expect(result).toEqual(user));
    const request = http.expectOne("http://localhost:9600/users/");
    expect(request.request.method).toBe("POST");
    expect(request.request.body).toEqual(user);
    request.flush(user);
  });

  it("updates a user", () => {
    service.editUser(3, user).subscribe(result => expect(result).toEqual(user));
    const request = http.expectOne("http://localhost:9600/users//user//edit3");
    expect(request.request.method).toBe("PUT");
    expect(request.request.body).toEqual(user);
    request.flush(user);
  });

  it("deletes a user", () => {
    service.deleteUser(3).subscribe(result => expect(result).toEqual({ deleted: true }));
    const request = http.expectOne("http://localhost:9600/users//user//edit/delete/3");
    expect(request.request.method).toBe("DELETE");
    request.flush({ deleted: true });
  });
});
