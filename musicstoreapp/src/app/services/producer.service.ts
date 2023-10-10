import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';

import { Producer } from '../interfaces/producer';
import { Auth } from '../interfaces/utility/auth';

import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ProducerService {
  private root_url = environment.apiBaseUrl;
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  private addressAPI: string = "producers";

  constructor(private http: HttpClient) { }

  public getAll(auth: Auth): Observable<Producer[]> {
    this.httpOptions.headers.append('userRequest', JSON.stringify(auth));
    return this.http.get<Producer[]>(this.root_url + this.addressAPI + '/all', this.httpOptions);
  }

  public getById(auth: Auth, producerId: string): Observable<Producer> {
    this.httpOptions.headers.append('userRequest', JSON.stringify(auth));
    return this.http.get<Producer>(this.root_url + this.addressAPI + '/' + producerId, this.httpOptions);
  }

  public save(auth: Auth, producer: Producer): Observable<Producer> {
    this.httpOptions.headers.append('userRequest', JSON.stringify(auth));
    return this.http.post<Producer>(this.root_url + this.addressAPI, producer, this.httpOptions);
  }

  public delete(auth: Auth, producerId: string): Observable<void> {
    this.httpOptions.headers.append('userRequest', JSON.stringify(auth));
    return this.http.delete<void>(this.root_url + this.addressAPI + '/' + producerId, this.httpOptions);
  }

  public getBest(limit: number): Observable<Producer[]> {
    return this.http.get<Producer[]>(this.root_url + this.addressAPI + '/best/' + limit, this.httpOptions);
  }
}
