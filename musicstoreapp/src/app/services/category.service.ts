import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';

import { Category } from '../interfaces/category';
import { WebUser } from '../interfaces/webuser';
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

  constructor(private http : HttpClient){}

  public getAllCategories(): Observable<Category[]>{
    return this.http.get<Category[]>(this.root_url + 'category');
  }

  public getByIdCategory(id: number): Observable<Category>{
    return this.http.get<Category>(this.root_url + 'category/' + id);
  }

  public createCategory(body: Body): Observable<Category>{
    return this.http.post<Category>(this.root_url + 'category', body);
  }

  public updateCategory(body: Body): Observable<Category>{
    return this.http.put<Category>(this.root_url + 'category/' + body.topost.id, body);
  }

  public deleteCategory(id: number, webuser: WebUser): Observable<void>{
    const options = {body: webuser};
    return this.http.delete<void>(this.root_url + 'category/' + id,options);
  }
}
