import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';

import { Customer } from '../interfaces/customer';
import { User } from '../interfaces/user';

import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {
  private root_url = environment.apiBaseUrl;
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  private addressAPI: string = "customers";

  constructor(private http: HttpClient) { }

  public getAll(user: User): Observable<Customer[]> {
    this.httpOptions.headers.append('user', JSON.stringify(user));
    return this.http.get<Customer[]>(this.root_url + this.addressAPI + '/all', this.httpOptions);
  }

  public getById(user: User): Observable<Customer> {
    this.httpOptions.headers.append('user', JSON.stringify(user));
    return this.http.get<Customer>(this.root_url + this.addressAPI, this.httpOptions);
  }

  public create(user: User, customer: Customer): Observable<Customer> {
    this.httpOptions.headers.append('user', JSON.stringify(user));
    return this.http.post<Customer>(this.root_url + this.addressAPI, customer, this.httpOptions);
  }

  public delete(user: User, customerId: number): Observable<void> {
    this.httpOptions.headers.append('user', JSON.stringify(user));
    return this.http.delete<void>(this.root_url + this.addressAPI + '/' + customerId, this.httpOptions);
  }
}
