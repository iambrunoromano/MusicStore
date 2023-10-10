import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';

import { Customer } from '../interfaces/customer';
import { Auth } from '../interfaces/utility/auth';

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

  public getAll(auth: Auth): Observable<Customer[]> {
    this.httpOptions.headers.append('userRequest', JSON.stringify(auth));
    return this.http.get<Customer[]>(this.root_url + this.addressAPI + '/all', this.httpOptions);
  }

  public getById(auth: Auth): Observable<Customer> {
    this.httpOptions.headers.append('userRequest', JSON.stringify(auth));
    return this.http.get<Customer>(this.root_url + this.addressAPI, this.httpOptions);
  }

  public create(auth: Auth, customer: Customer): Observable<Customer> {
    this.httpOptions.headers.append('userRequest', JSON.stringify(auth));
    return this.http.post<Customer>(this.root_url + this.addressAPI, customer, this.httpOptions);
  }

  public delete(auth: Auth, customerId: number): Observable<void> {
    this.httpOptions.headers.append('userRequest', JSON.stringify(auth));
    return this.http.delete<void>(this.root_url + this.addressAPI + '/' + customerId, this.httpOptions);
  }
}
