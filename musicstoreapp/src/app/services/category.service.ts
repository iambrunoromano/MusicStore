import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';

import { Category } from '../interfaces/category';
import { Auth } from '../interfaces/utility/auth';

import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  private root_url = environment.apiBaseUrl;
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  private addressAPI: string = "categories";

  constructor(private http: HttpClient) { }

  public getAll(): Observable<Category[]> {
    return this.http.get<Category[]>(this.root_url + this.addressAPI + '/all', this.httpOptions);
  }

  public getById(categoryId: number): Observable<Category> {
    return this.http.get<Category>(this.root_url + this.addressAPI + '/' + categoryId, this.httpOptions);
  }

  public update(auth: Auth, category: Category): Observable<Category> {
    this.httpOptions.headers.append('userRequest', JSON.stringify(auth));
    return this.http.post<Category>(this.root_url + this.addressAPI, category, this.httpOptions);
  }

  public delete(auth: Auth, categoryId: number): Observable<void> {
    this.httpOptions.headers.append('userRequest', JSON.stringify(auth));
    return this.http.delete<void>(this.root_url + this.addressAPI + '/' + categoryId, this.httpOptions);
  }

  public getByProducer(producerId: String): Observable<Category[]> {
    return this.http.get<Category[]>(this.root_url + this.addressAPI + '/' + producerId + this.addressAPI);
  }
}
