import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { UserModel } from './user.component';
import { HttpHeaders } from '@angular/common/http';

/**
 * Injectable
 */
@Injectable({
  providedIn: "root"
})
export class UserService {
  userByIdUrl = "http://localhost:9100/users/";
  users = "http://localhost:9100/users";
  edit = "/edit";
  user = "/user/";
  add = "/add";
  delete = "/delete/";
  headers = new HttpHeaders({
    "Content-Type": "application/json"
  });
  
  constructor(private http: HttpClient) {}

  /**
   * Adds a user
   * @param user
   * @returns user
   */
  addUser(user: UserModel): Observable<any> {
    return this.http.post(this.userByIdUrl + this.user + this.edit + this.add, {
      responseType: "json"
    });
  }

  /**
   * Saves a user
   * @param user
   * @returns user
   */
  saveUser(user: UserModel): Observable<any> {
    return this.http.post(this.userByIdUrl, user, { responseType: "json" });
  }

  /**
   * Edits a user
   * 
   * @param user
   * @returns user
   */
  editUser(userId: number, user: UserModel): Observable<any> {
    return this.http.put(this.userByIdUrl + this.user + this.edit + userId, user, {
      responseType: "json"
    });
  }

  /**
   * Deletes a user
   * 
   * @param user
   * @returns user
   */
  deleteUser(userId: number): Observable<any> {
    return this.http.delete(this.userByIdUrl + this.user + this.edit + this.delete+userId, {
      headers: this.headers,
      responseType: "json"
    });
  }

  /**
   * Gets user by id
   * 
   * @param userId
   * @returns user by id
   */
  getUserById(userId: Number): Observable<any> {
    let finalUserUrl = this.userByIdUrl + userId;
    console.log("finalUserUrl: " + finalUserUrl);

    return this.http.get(finalUserUrl, { responseType: "json" });
  }

  /**
   * Gets user
   * @returns user
   */
  getUsers(): Observable<any> {
    return this.http.get(this.users, { responseType: "json" });
  }
}