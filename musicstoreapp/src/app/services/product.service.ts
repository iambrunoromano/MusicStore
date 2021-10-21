import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';

import { Product } from '../interfaces/product';
import { Body } from '../interfaces/body';

import { environment } from 'src/environments/environment';

@Injectable({
  providedIn : 'root'
})
export class ProductService{
  private root_url = environment.apiBaseUrl;
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type' : 'application/json'
    })
  }

  constructor(private http : HttpClient){}

  public getAllProducts(): Observable<Product[]>{
    return this.http.get<Product[]>(this.root_url + 'product');
  }

  public getByIdProduct(id: number): Observable<Product>{
    return this.http.get<Product>(this.root_url + 'product/' + id);
  }

  public createProduct(body: Body): Observable<Product>{
    return this.http.post<Product>(this.root_url + 'product', body);
  }

  public updateProduct(body: Body): Observable<Product>{
    return this.http.put<Product>(this.root_url + 'product/' + body.topost.id, body);
  }

  public deleteProduct(body: Body): Observable<void>{
    const options = {
      /*headers: new HttpHeaders({}),*/
      body: body};
    return this.http.delete<void>(this.root_url + 'product/' + body.topost.id,options);
  }
}
