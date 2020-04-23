import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HttpHeaders, HttpParams } from '@angular/common/http';

/**
 * Injectable
 */
@Injectable({
  providedIn: 'root'
})
export class AccountService {
  getAccountUrl = "http://localhost:9600/accounts";
  getAccountByIdUrl = "http://localhost:9600/accounts/";
  headers: any;
  id: any;
  auth = "user:password";

  constructor(private http: HttpClient) {
    this.headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Content-Encoding': 'none',
      'Authorization': 'Basic ' + btoa(this.auth)
    });
  }

  /**
   * Gets accounts
   * @returns accounts 
   */
  getAccounts(): Observable<any> {
    console.log("Hello1");
    console.log("headers: " + this.headers);
    console.log("url: " + this.getAccountUrl);
    console.log("this.http.get(url)" + this.http.get(this.getAccountUrl));
    return this.http.get(this.getAccountUrl, { responseType: 'json' });
    //return this.http.get(this.url, {responseType: 'text'}, this.headers);
  }

  /**
   * Gets accounts by id
   * @param accountId 
   * @returns accounts by id 
   */
  getAccountsById(accountId: number): Observable<any> {
    console.log("Hello1");
    console.log("headers: " + this.headers);
    console.log("this.getAccountByIdUrl: " + this.getAccountByIdUrl);
    console.log("accountId: " + accountId);
    let getAccountByIdUrlFinal = this.getAccountByIdUrl + String(accountId);
    console.log("getAccountByIdUrlFinal: " + getAccountByIdUrlFinal);


    console.log("this.http.get(url)" + this.http.get(getAccountByIdUrlFinal));
    return this.http.get(getAccountByIdUrlFinal, { responseType: 'json' });
    //return this.http.get(this.url, {responseType: 'text'}, this.headers);
  }

}
