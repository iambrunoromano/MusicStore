import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';

import { LogStatus } from '../interfaces/logstatus';
import { Body } from '../interfaces/body';
import { WebUser } from '../interfaces/webuser';

import { environment } from 'src/environments/environment';

@Injectable({
  providedIn : 'root'
})
export class WebUserService{
  private root_url = environment.apiBaseUrl;
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type' : 'application/json'
    })
  }

  private addressAPI: string = "webuser";

  constructor(private http : HttpClient){}

  public getAll(wu: WebUser): Observable<WebUser[]>{
    return this.http.post<WebUser[]>(this.root_url + this.addressAPI + '/all',wu);
  }

  public getById(body: Body): Observable<WebUser>{
    return this.http.post<WebUser>(this.root_url + this.addressAPI + '/' + body.topost.mail,body.authorized);
  }

  public login(wu: WebUser): Observable<LogStatus>{
    return this.http.post<LogStatus>(this.root_url + this.addressAPI + 'login',wu);
  }

  public logout(wu: WebUser): Observable<LogStatus>{
    return this.http.post<LogStatus>(this.root_url + this.addressAPI + 'logout',wu);
  }

  public create(wu: WebUser): Observable<WebUser>{
    return this.http.post<WebUser>(this.root_url + this.addressAPI,wu);
  }

  public update(body: Body): Observable<WebUser>{
    return this.http.put<WebUser>(this.root_url + this.addressAPI + '/' + body.topost.mail,body);
  }

  public delete(body: Body): Observable<void>{
    const options = {body: body.authorized};
    return this.http.delete<void>(this.root_url + this.addressAPI + '/' + body.topost.mail,options);
  }
}
