import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';

import { Order } from '../interfaces/order';
import { OrderResponse } from '../interfaces/response/orderresponse';
import { User } from '../interfaces/user';

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

  public getAll(user: User): Observable<Order[]> {
    this.httpOptions.headers.append('user', JSON.stringify(user));
    return this.http.get<Order[]>(this.root_url + this.addressAPI + '/all', this.httpOptions);
  }

  public getById(user: User, orderId: number): Observable<Order> {
    this.httpOptions.headers.append('user', JSON.stringify(user));
    return this.http.get<Order>(this.root_url + this.addressAPI + '/' + orderId, this.httpOptions);
  }

  public create(user: User, address: string): Observable<OrderResponse> {
    this.httpOptions.headers.append('user', JSON.stringify(user));
    return this.http.post<OrderResponse>(this.root_url + this.addressAPI, address, this.httpOptions);
  }

  public delete(user: User, orderId: number): Observable<void> {
    this.httpOptions.headers.append('user', JSON.stringify(user));
    return this.http.delete<void>(this.root_url + this.addressAPI + '/' + orderId, this.httpOptions);
  }
}
