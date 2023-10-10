import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';

import { Auth } from '../interfaces/utility/auth';
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

  public getCart(auth: Auth): Observable<Cart[]> {
    this.httpOptions.headers.append('userRequest', JSON.stringify(auth));
    return this.http.get<Cart[]>(this.root_url + this.addressAPI, this.httpOptions);
  }

  public save(auth: Auth, cartRequest: CartRequest): Observable<void> {
    this.httpOptions.headers.append('userRequest', JSON.stringify(auth));
    return this.http.post<void>(this.root_url + this.addressAPI, cartRequest, this.httpOptions);
  }

  public delete(auth: Auth): Observable<void> {
    this.httpOptions.headers.append('userRequest', JSON.stringify(auth));
    return this.http.delete<void>(this.root_url + this.addressAPI, this.httpOptions);
  }
}
