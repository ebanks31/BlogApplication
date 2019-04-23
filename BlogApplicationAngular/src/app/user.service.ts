import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

/**
 * Injectable
 */
@Injectable({
  providedIn: 'root'
})
export class UserService {
  userByIdUrl = "http://localhost:9100/users/";
  users = "http://localhost:9100/users";

  constructor(private http: HttpClient) {
  }

  /**
   * Adds user
   * @param user 
   * @returns user 
   */
  addUser(user: string): Observable<any> {
    return this.http.post(this.userByIdUrl, user, { responseType: 'json' });
  }

  /**
   * Saves user
   * @param user 
   * @returns user 
   */
  saveUser(user: string): Observable<any> {
    return this.http.post(this.userByIdUrl, user, { responseType: 'json' });
  }

  /**
   * Gets user by id
   * @param userId 
   * @returns user by id 
   */
  getUserById(userId: Number): Observable<any> {
    let finalUserUrl = this.userByIdUrl + userId;
    console.log("finalUserUrl: " + finalUserUrl);

    return this.http.get(finalUserUrl, { responseType: 'json' });
  }

  /**
   * Gets user
   * @param userId 
   * @returns user 
   */
  getUser(userId: Number): Observable<any> {
    return this.http.get(this.users, { responseType: 'json' });
  }
}

