import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';

import { Body } from '../interfaces/body';
import { WebUser } from '../interfaces/webuser';
import { Admin } from '../interfaces/admin';

import { environment } from 'src/environments/environment';

@Injectable({
  providedIn : 'root'
})
export class AdminService{
  private root_url = environment.apiBaseUrl;
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type' : 'application/json'
    })
  }

  private addressAPI: string = "admin";

  constructor(private http : HttpClient){}

  public getAll(wu: WebUser): Observable<Admin[]>{
    return this.http.post<Admin[]>(this.root_url + this.addressAPI + '/all',wu);
  }

  public getById(body: Body): Observable<Admin>{
    return this.http.post<Admin>(this.root_url + this.addressAPI + '/' + body.topost.mail,body.authorized);
  }

  public create(body: Body): Observable<Admin>{
    return this.http.post<Admin>(this.root_url + this.addressAPI,body);
  }

  public update(body: Body): Observable<Admin>{
    return this.http.put<Admin>(this.root_url + this.addressAPI + '/' + body.topost.mail,body);
  }

  public delete(body: Body): Observable<void>{
    const options = {body: body.authorized};
    return this.http.delete<void>(this.root_url + this.addressAPI + '/' + body.topost.mail,options);
  }
}
