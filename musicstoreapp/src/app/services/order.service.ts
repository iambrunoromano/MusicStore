import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';

import { Order } from '../interfaces/order';
import { Body } from '../interfaces/body';
import { WebUser } from '../interfaces/webuser';

import { environment } from 'src/environments/environment';

@Injectable({
  providedIn : 'root'
})
export class OrderService{
  private root_url = environment.apiBaseUrl;
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type' : 'application/json'
    })
  }

  private addressAPI: string = "order";

  constructor(private http : HttpClient){}

  public getAll(wu: WebUser): Observable<Order[]>{
    return this.http.post<Order[]>(this.root_url + this.addressAPI + '/all',wu);
  }

    public getById(body: Body): Observable<Order>{
      return this.http.post<Order>(this.root_url + this.addressAPI + '/' + body.topost.id,body.authorized);
    }

    public create(body: Body): Observable<Order>{
      return this.http.post<Order>(this.root_url + this.addressAPI, body.authorized);
    }

    public update(body: Body): Observable<Order>{
      return this.http.put<Order>(this.root_url + this.addressAPI + '/' + body.topost.id,body);
    }

    public delete(body: Body): Observable<void>{
      const options = {body: body.authorized};
      return this.http.delete<void>(this.root_url + this.addressAPI + '/' + body.topost.id,options);
    }
}
