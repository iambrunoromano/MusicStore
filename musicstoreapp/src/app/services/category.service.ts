import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';

import { Category } from '../interfaces/category';
import { WebUser } from '../interfaces/webuser';

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

  public getByIdCategory(): Observable<Category>{
    return this.http.get<Category>(this.root_url + 'category/' + 3);
  }

  /**public createCategory(category : Category): Observable<Category>{
    return this.http.post(this.root_url + 'category', category,httpOptions);
  }

  public updateCategory(id : number, category : Category): Observable<Category>{
    return this.http.put<Category>(this.root_url + 'category/' + id, category);
  }

  public deleteCategory(id : number, webuser : WebUser): void{
    this.http.delete(this.root_url + 'category/' + id);
  }**/

}
