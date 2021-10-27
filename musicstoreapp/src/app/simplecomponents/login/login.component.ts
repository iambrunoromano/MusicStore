import { Component, OnInit } from '@angular/core';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

import { HttpErrorResponse } from '@angular/common/http';

import { LogStatus } from '../../interfaces/logstatus';
import { WebUser } from '../../interfaces/webuser';
import { Body } from '../../interfaces/body';
import { WebUserService } from '../../services/webuser.service';
import { DataService } from '../../services/data.service';

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
              private dataService : DataService,
              private router : Router) { }

  ngOnInit(): void {
    this.dataService.setLogStatus(false);
    this.dataService.setUserData('','');
  }

  public login(): void{
    let wu : WebUser = {mail:(<HTMLInputElement>document.getElementById("username"))?.value,
                        password:(<HTMLInputElement>document.getElementById("password"))?.value};
    this.webuserService.login(wu).subscribe(
      (response: LogStatus) => {
        this.logstatus = response;
        this.dataService.setUserData(wu.mail, wu.password);
        this.dataService.setLogStatus(true);
        this.router.navigate(['/']);
      },
      (error : HttpErrorResponse) => {
        alert(error.message);
        this.router.navigate(['/login']);
      }
    );
  }
}
