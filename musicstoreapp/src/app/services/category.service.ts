import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';

import { Category } from '../interfaces/category';
import { Body } from '../interfaces/body';

import { environment } from 'src/environments/environment';

@Injectable({
  providedIn : 'root'
})
export class CategoryService{
  private root_url = environment.apiBaseUrl;
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type' : 'application/json'
    })
  }

  private addressAPI: string = "category";

  constructor(private http : HttpClient){}

  public getAll(): Observable<Category[]>{
    return this.http.get<Category[]>(this.root_url + this.addressAPI);
  }

  public getById(id: number): Observable<Category>{
    return this.http.get<Category>(this.root_url + this.addressAPI + '/' + id);
  }

  public create(body: Body): Observable<Category>{
    return this.http.post<Category>(this.root_url + this.addressAPI, body);
  }

  public update(body: Body): Observable<Category>{
    return this.http.put<Category>(this.root_url + this.addressAPI + '/' + body.topost.id, body);
  }

  public delete(body: Body): Observable<void>{
    const options = {body: body.authorized};
    return this.http.delete<void>(this.root_url + this.addressAPI + '/' + body.topost.id,options);
  }
}
