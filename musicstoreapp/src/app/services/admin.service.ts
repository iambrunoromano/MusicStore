import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';

import { User } from '../interfaces/user';
import { Admin } from '../interfaces/admin';

import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  private root_url = environment.apiBaseUrl;
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  private addressAPI: string = "admins";

  constructor(private http: HttpClient) { }

  public getAll(user: User): Observable<Admin[]> {
    this.httpOptions.headers.append('user', JSON.stringify(user));
    return this.http.get<Admin[]>(this.root_url + this.addressAPI + '/all', this.httpOptions);
  }

  public getById(user: User, adminId: string): Observable<Admin> {
    this.httpOptions.headers.append('user', JSON.stringify(user));
    return this.http.get<Admin>(this.root_url + this.addressAPI + '/' + adminId, this.httpOptions);
  }

  public save(user: User, admin: Admin): Observable<Admin> {
    this.httpOptions.headers.append('user', JSON.stringify(user));
    return this.http.post<Admin>(this.root_url + this.addressAPI, admin, this.httpOptions);
  }

  public delete(user: User, adminId: string): Observable<void> {
    this.httpOptions.headers.append('user', JSON.stringify(user));
    return this.http.delete<void>(this.root_url + this.addressAPI + '/' + adminId, this.httpOptions);
  }
}
