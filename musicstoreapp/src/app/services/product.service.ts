import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';

import { Product } from '../interfaces/product';
import { WebUser } from '../interfaces/webuser';

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

  public getByIdProduct(): Observable<Product>{
    return this.http.get<Product>(this.root_url + 'product/' + 3);
  }

}
