import { Injectable } from '@angular/core';

import { LogStatus } from '../interfaces/logstatus';
import { Body } from '../interfaces/body';
import { WebUser } from '../interfaces/webuser';

@Injectable({
  providedIn : 'root'
})
export class DataService{

  private userData = <WebUser>{ };
  private logStatus = <LogStatus>{ };
  private body = <Body>{ };

  constructor(){
    this.initUserData();
    this.setLogStatus(false);
  }

  public getUserData(): WebUser{
    return this.userData;
  }

  public setUserData(mail: string, password: string): void{
    this.userData.mail = mail;
    this.userData.password = password;
    this.body.authorized = this.userData;
  }

  public initUserData(): void{
    this.userData.mail = '';
    this.userData.password = '';
  }

  public getLogStatus(): LogStatus{
    return this.logStatus;
  }

  public setLogStatus(status: boolean): void{
    this.logStatus.logstatus = status;
  }

  public setToPostBody(obj: any): Body{
    this.body.topost = obj;
    return this.body;
  }
}
