import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';

import { Customer } from '../interfaces/customer';
import { Body } from '../interfaces/body';
import { WebUser } from '../interfaces/webuser';

import { environment } from 'src/environments/environment';

@Injectable({
  providedIn : 'root'
})
export class CustomerService{
  private root_url = environment.apiBaseUrl;
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type' : 'application/json'
    })
  }

  private addressAPI: string = "customer";

  constructor(private http : HttpClient){}

  public getAll(wu: WebUser): Observable<Customer[]>{
    return this.http.post<Customer[]>(this.root_url + this.addressAPI + '/all',wu);
  }

    public getById(body: Body): Observable<Customer>{
      return this.http.post<Customer>(this.root_url + this.addressAPI + '/' + body.topost.mail,body.authorized);
    }

    public create(body: Body): Observable<Customer>{
      return this.http.post<Customer>(this.root_url + this.addressAPI,body.topost);
    }

    public update(body: Body): Observable<Customer>{
      return this.http.put<Customer>(this.root_url + this.addressAPI + '/' + body.topost.mail,body);
    }

    public delete(body: Body): Observable<void>{
      const options = {body: body.authorized};
      return this.http.delete<void>(this.root_url + this.addressAPI + '/' + body.topost.mail,options);
    }
}
