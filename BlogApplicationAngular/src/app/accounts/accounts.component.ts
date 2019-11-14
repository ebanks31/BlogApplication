import { Component, OnInit } from '@angular/core';
import { AccountService } from '../account.service';
import { AccountModel } from '../account.component';

/**
 * Component
 */
@Component({
  selector: 'app-accounts',
  templateUrl: './accounts.component.html',
  styleUrls: ['./accounts.component.css']
})
export class AccountsComponent implements OnInit {
  accounts: AccountModel[];
  constructor(private accountService: AccountService) { }

  /**
   * on init
   */
  ngOnInit() {
    this.getAccounts();
    console.log("this.v: " + this.accounts)

  }

  /**
   * Gets accounts
   */
  getAccounts(): void {
    this.accountService.getAccounts()
      .subscribe(data => this.accounts = data,
        err => console.log(err),() => console.log('Got all accounts'))
  }

  /**
   * Converts to json
   * @param account 
   * @returns  
   */
  ConvertToJSON(account: any) {
    console.log("account " + account);
    return JSON.parse(account);
  }
}
