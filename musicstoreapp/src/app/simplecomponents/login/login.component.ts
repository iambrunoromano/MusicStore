import { Component, OnInit } from '@angular/core';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';

import { HttpErrorResponse } from '@angular/common/http';

import { User } from '../../interfaces/user';
import { UserResponse } from '../../interfaces/response/userresponse';
import { UserService } from '../../services/user.service';
import { DataService } from '../../services/data.service';

@Injectable({
  providedIn: 'root'
})
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private userService: UserService,
    private dataService: DataService,
    private router: Router) { }

  ngOnInit(): void {
    this.dataService.setLogStatus(false);
    this.dataService.setUserData('', '');
  }

  public login(): void {
    let user: User = {
      mail: (<HTMLInputElement>document.getElementById("username"))?.value,
      password: (<HTMLInputElement>document.getElementById("password"))?.value,
      imgurl: ''
    };
    this.userService.getById(user).subscribe(
      (response: UserResponse) => {
        this.dataService.setUserData(response.mail, user.password);
        this.dataService.setLogStatus(true);
        this.router.navigate(['/']);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
        this.router.navigate(['/login']);
      }
    );
  }
}
