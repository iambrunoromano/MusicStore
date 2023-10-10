import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';

import { Admin } from '../interfaces/admin';

import { environment } from 'src/environments/environment';
import { Auth } from '../interfaces/utility/auth';

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

  public getAll(auth: Auth): Observable<Admin[]> {
    this.httpOptions.headers.append('userRequest', JSON.stringify(auth));
    return this.http.get<Admin[]>(this.root_url + this.addressAPI + '/all', this.httpOptions);
  }

  public getById(auth: Auth, adminId: string): Observable<Admin> {
    this.httpOptions.headers.append('userRequest', JSON.stringify(auth));
    return this.http.get<Admin>(this.root_url + this.addressAPI + '/' + adminId, this.httpOptions);
  }

  public save(auth: Auth, admin: Admin): Observable<Admin> {
    this.httpOptions.headers.append('userRequest', JSON.stringify(auth));
    return this.http.post<Admin>(this.root_url + this.addressAPI, admin, this.httpOptions);
  }

  public delete(auth: Auth, adminId: string): Observable<void> {
    this.httpOptions.headers.append('userRequest', JSON.stringify(auth));
    return this.http.delete<void>(this.root_url + this.addressAPI + '/' + adminId, this.httpOptions);
  }
}
