import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';

import { User } from '../interfaces/user';
import { UserResponse } from '../interfaces/userresponse';

import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private root_url = environment.apiBaseUrl;
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  private addressAPI: string = "users";

  constructor(private http: HttpClient) { }

  public getAll(user: User): Observable<UserResponse[]> {
    this.httpOptions.headers.append('user', JSON.stringify(user));
    return this.http.get<UserResponse[]>(this.root_url + this.addressAPI + '/all', this.httpOptions);
  }

  public getById(user: User): Observable<UserResponse> {
    this.httpOptions.headers.append('user', JSON.stringify(user));
    return this.http.get<UserResponse>(this.root_url + this.addressAPI, this.httpOptions);
  }

  public save(user: User): Observable<UserResponse> {
    this.httpOptions.headers.append('user', JSON.stringify(user));
    return this.http.post<UserResponse>(this.root_url + this.addressAPI, this.httpOptions);
  }

  public delete(user: User): Observable<void> {
    this.httpOptions.headers.append('user', JSON.stringify(user));
    return this.http.delete<void>(this.root_url + this.addressAPI, this.httpOptions);
  }
}
