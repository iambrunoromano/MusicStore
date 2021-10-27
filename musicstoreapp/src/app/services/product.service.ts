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

  private addressAPI: string = "product";

  constructor(private http : HttpClient){}

  public ProductsByCategory(id: String): Observable<Product[]>{
    return this.http.get<Product[]>(this.root_url + this.addressAPI + '/category/' + id);
  }

  public ProductsByProducer(mail: String): Observable<Product[]>{
    return this.http.get<Product[]>(this.root_url + this.addressAPI + '/' + mail + '/products');
  }

  public bestProducts(): Observable<Product[]>{
    return this.http.get<Product[]>(this.root_url + this.addressAPI + '/best');
  }

  public getAll(): Observable<Product[]>{
    return this.http.get<Product[]>(this.root_url + this.addressAPI + '/all');
  }

  public getById(id: number): Observable<Product>{
    return this.http.get<Product>(this.root_url + this.addressAPI + '/' + id);
  }

  public create(body: Body): Observable<Product>{
    return this.http.post<Product>(this.root_url + this.addressAPI, body);
  }

  public update(body: Body): Observable<Product>{
    return this.http.put<Product>(this.root_url + this.addressAPI + '/' + body.topost.id, body);
  }

  public delete(body: Body): Observable<void>{
    const options = {body: body.authorized};
    return this.http.delete<void>(this.root_url + this.addressAPI + '/' +  body.topost.id,options);
  }
}
