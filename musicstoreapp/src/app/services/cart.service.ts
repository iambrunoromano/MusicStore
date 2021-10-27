import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';

import { Body } from '../interfaces/body';
import { WebUser } from '../interfaces/webuser';
import { Cart } from '../interfaces/cart';
import { Product } from '../interfaces/product';

import { environment } from 'src/environments/environment';

@Injectable({
  providedIn : 'root'
})
export class CartService{
  private root_url = environment.apiBaseUrl;
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type' : 'application/json'
    })
  }

  private addressAPI: string = "cart";

  constructor(private http : HttpClient){}

  public ProductsByCart(wu: WebUser): Observable<Product[]>{
    return this.http.post<Product[]>(this.root_url + this.addressAPI + '/' + wu.mail + '/products',wu);
  }

  public getAll(wu: WebUser): Observable<Cart[]>{
    return this.http.post<Cart[]>(this.root_url + this.addressAPI + '/all',wu);
  }

  public getById(body: Body): Observable<Cart>{
    return this.http.post<Cart>(this.root_url + this.addressAPI + '/' + body.topost.id,body.authorized);
  }

  public create(body: Body): Observable<Cart>{
    return this.http.post<Cart>(this.root_url + this.addressAPI,body);
  }

  public update(body: Body): Observable<Cart>{
    return this.http.put<Cart>(this.root_url + this.addressAPI + '/' + body.topost.id,body);
  }

  public delete(body: Body): Observable<void>{
    const options = {body: body.authorized};
    return this.http.delete<void>(this.root_url + this.addressAPI + '/' + body.topost.id,options);
  }
}
