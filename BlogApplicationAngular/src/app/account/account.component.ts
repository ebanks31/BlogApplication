import { Component, OnInit } from "@angular/core";
import { AccountService } from "../account.service";
import { AccountModel } from "../account.component";
import { ActivatedRoute } from "@angular/router";
import { UserModel } from "../user.component";
import { UserService } from "../user.service";

/**
 * Component
 */
@Component({
  selector: "app-account",
  templateUrl: "./account.component.html",
  styleUrls: ["./account.component.css"]
})
export class AccountComponent implements OnInit {
  account: AccountModel;
  user: UserModel;
  id: number;

  constructor(
    private accountService: AccountService,
    private userService: UserService,
    private route: ActivatedRoute
  ) {
    this.route.params.subscribe(
      params => (this.id = params.id),
      error => console.log(error)
    );
  }
  /**
   * on init
   */
  ngOnInit() {
    this.getAccountsById(this.id);
    this.getUserById(1);
    console.log("this.account: " + this.account);
    console.log("this.user: " + this.user);
  }
  /**
   * Gets accounts by id
   * @param id
   */
  getAccountsById(id: number): void {
    console.log("getAccountsById()");

    console.log("this.id: " + this.id);

    this.accountService.getAccountsById(this.id).subscribe(
      data => (this.account = data),
      error => console.log(error)
    );
    console.log("account " + this.account);
  }
  /**
   * Saves account
   * @param $event
   */
  saveAccount($event: any): void {
    console.log("saveAccount");
  }
  /**
   * Gets user by id
   * @param userId
   */
  getUserById(userId: number) {
    console.log("getUserById()");

    this.userService.getUserById(this.id).subscribe(
      data => (this.user = data),
      error => console.log(error)
    );
  }
}
