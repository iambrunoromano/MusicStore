import { Component, OnInit } from '@angular/core';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

import { HttpErrorResponse } from '@angular/common/http';

import { LogStatus } from '../../interfaces/logstatus';
import { WebUser } from '../../interfaces/webuser';
import { Body } from '../../interfaces/body';
import { WebUserService } from '../../services/webuser.service';

@Injectable({
  providedIn : 'root'
})
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public logstatus = <LogStatus>{ };

  constructor(private webuserService : WebUserService,
              private router : Router) { }

  ngOnInit(): void {
  }

  public login(): void{
    let wu : WebUser = {mail:(<HTMLInputElement>document.getElementById("username"))?.value,
                        password:(<HTMLInputElement>document.getElementById("password"))?.value};
    /*let body: Body = {authorized: wu};
    console.log(body.authorized);*/
    this.webuserService.Login(wu).subscribe(
      (response: LogStatus) => {
        this.logstatus = response;
        this.router.navigate(['/']);
      },
      (error : HttpErrorResponse) => {
        this.router.navigate(['/login']);
      }
    );
  }
}
