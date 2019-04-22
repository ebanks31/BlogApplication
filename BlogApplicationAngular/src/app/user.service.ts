import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  userByIdUrl = "http://localhost:9100/users/";
  users = "http://localhost:9100/users";
  constructor(private http: HttpClient) {
  }

  addUser(user: string): Observable<any> {
    return this.http.post(this.userByIdUrl, user, { responseType: 'json' });
  }

  saveUser(user: string): Observable<any> {
    return this.http.post(this.userByIdUrl, user, { responseType: 'json' });
  }

  getUserById(userId: Number): Observable<any> {
    let finalUserUrl = this.userByIdUrl + userId;
    console.log("finalUserUrl: " + finalUserUrl);

    return this.http.get(finalUserUrl, { responseType: 'json' });
  }

  getUser(userId: Number): Observable<any> {
    return this.http.get(this.users, { responseType: 'json' });
  }
}

