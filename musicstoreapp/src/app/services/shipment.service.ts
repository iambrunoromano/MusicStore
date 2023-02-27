import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';

import { Shipment } from '../interfaces/shipment';
import { User } from '../interfaces/user';

import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ShipmentService {
  private root_url = environment.apiBaseUrl;
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  private addressAPI: string = "shipments";

  constructor(private http: HttpClient) { }

  public getAll(user: User): Observable<Shipment[]> {
    this.httpOptions.headers.append('user', JSON.stringify(user));
    return this.http.get<Shipment[]>(this.root_url + this.addressAPI + '/all', this.httpOptions);
  }

  public getById(user: User, shipmentId: number): Observable<Shipment> {
    this.httpOptions.headers.append('user', JSON.stringify(user));
    return this.http.get<Shipment>(this.root_url + this.addressAPI + '/' + shipmentId, this.httpOptions);
  }

  public save(user: User, orderId: number): Observable<Shipment> {
    this.httpOptions.headers.append('user', JSON.stringify(user));
    return this.http.post<Shipment>(this.root_url + this.addressAPI + '/' + orderId, this.httpOptions);
  }

  public delete(user: User, orderId: number): Observable<void> {
    this.httpOptions.headers.append('user', JSON.stringify(user));
    return this.http.delete<void>(this.root_url + this.addressAPI + '/' + orderId, this.httpOptions);
  }
}
