import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';

import { Auth } from '../interfaces/utility/auth';
import { UserResponse } from '../interfaces/response/userresponse';

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

  public getAll(auth: Auth): Observable<UserResponse[]> {
    this.httpOptions.headers.append('userRequest', JSON.stringify(auth));
    return this.http.get<UserResponse[]>(this.root_url + this.addressAPI + '/all', this.httpOptions);
  }

  public getById(auth: Auth): Observable<UserResponse> {
    this.httpOptions.headers.append('userRequest', JSON.stringify(auth));
    return this.http.get<UserResponse>(this.root_url + this.addressAPI, this.httpOptions);
  }

  public save(auth: Auth): Observable<UserResponse> {
    this.httpOptions.headers.append('userRequest', JSON.stringify(auth));
    return this.http.post<UserResponse>(this.root_url + this.addressAPI, this.httpOptions);
  }

  public delete(auth: Auth): Observable<void> {
    this.httpOptions.headers.append('userRequest', JSON.stringify(auth));
    return this.http.delete<void>(this.root_url + this.addressAPI, this.httpOptions);
  }
}
