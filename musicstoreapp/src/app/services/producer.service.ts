import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';

import { Producer } from '../interfaces/producer';
import { User } from '../interfaces/user';

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

  public getAll(user: User): Observable<Producer[]> {
    this.httpOptions.headers.append('user', JSON.stringify(user));
    return this.http.get<Producer[]>(this.root_url + this.addressAPI + '/all', this.httpOptions);
  }

  public getById(user: User, producerId: string): Observable<Producer> {
    this.httpOptions.headers.append('user', JSON.stringify(user));
    return this.http.get<Producer>(this.root_url + this.addressAPI + '/' + producerId, this.httpOptions);
  }

  public save(user: User, producer: Producer): Observable<Producer> {
    this.httpOptions.headers.append('user', JSON.stringify(user));
    return this.http.post<Producer>(this.root_url + this.addressAPI, producer, this.httpOptions);
  }

  public delete(user: User, producerId: string): Observable<void> {
    this.httpOptions.headers.append('user', JSON.stringify(user));
    return this.http.delete<void>(this.root_url + this.addressAPI + '/' + producerId, this.httpOptions);
  }
}
