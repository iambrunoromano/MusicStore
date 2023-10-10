import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';

import { Order } from '../interfaces/order';
import { OrderResponse } from '../interfaces/response/orderresponse';
import { Auth } from '../interfaces/utility/auth';

import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  private root_url = environment.apiBaseUrl;
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  private addressAPI: string = "orders";

  constructor(private http: HttpClient) { }

  public getAll(auth: Auth,): Observable<Order[]> {
    this.httpOptions.headers.append('userRequest', JSON.stringify(auth));
    return this.http.get<Order[]>(this.root_url + this.addressAPI + '/all', this.httpOptions);
  }

  public getById(auth: Auth, orderId: number): Observable<Order> {
    this.httpOptions.headers.append('userRequest', JSON.stringify(auth));
    return this.http.get<Order>(this.root_url + this.addressAPI + '/' + orderId, this.httpOptions);
  }

  public create(auth: Auth, address: string): Observable<OrderResponse> {
    this.httpOptions.headers.append('userRequest', JSON.stringify(auth));
    return this.http.post<OrderResponse>(this.root_url + this.addressAPI, address, this.httpOptions);
  }

  public delete(auth: Auth, orderId: number): Observable<void> {
    this.httpOptions.headers.append('userRequest', JSON.stringify(auth));
    return this.http.delete<void>(this.root_url + this.addressAPI + '/' + orderId, this.httpOptions);
  }
}
