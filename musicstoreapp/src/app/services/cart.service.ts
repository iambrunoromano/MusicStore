import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';

import { User } from '../interfaces/user';
import { Cart } from '../interfaces/cart';
import { CartRequest } from '../interfaces/cartrequest';

import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private root_url = environment.apiBaseUrl;
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  private addressAPI: string = "carts";

  constructor(private http: HttpClient) { }

  public getCart(user: User): Observable<Cart[]> {
    this.httpOptions.headers.append('user', JSON.stringify(user));
    return this.http.get<Cart[]>(this.root_url + this.addressAPI, this.httpOptions);
  }

  public save(user: User, cartRequest: CartRequest): Observable<void> {
    this.httpOptions.headers.append('user', JSON.stringify(user));
    return this.http.post<void>(this.root_url + this.addressAPI, cartRequest, this.httpOptions);
  }

  public delete(user: User): Observable<void> {
    this.httpOptions.headers.append('user', JSON.stringify(user));
    return this.http.delete<void>(this.root_url + this.addressAPI, this.httpOptions);
  }
}
