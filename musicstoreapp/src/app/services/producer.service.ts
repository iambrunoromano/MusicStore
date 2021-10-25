import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';

import { Producer } from '../interfaces/producer';
import { Body } from '../interfaces/body';
import { WebUser } from '../interfaces/webuser';

import { environment } from 'src/environments/environment';

@Injectable({
  providedIn : 'root'
})
export class ProducerService{
  private root_url = environment.apiBaseUrl;
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type' : 'application/json'
    })
  }

  private addressAPI: string = "producer";

  constructor(private http : HttpClient){}

  public bestProducers(): Observable<Producer[]>{
    return this.http.get<Producer[]>(this.root_url + this.addressAPI + '/best');
  }

  public getAll(wu: WebUser): Observable<Producer[]>{
    return this.http.post<Producer[]>(this.root_url + this.addressAPI + '/all',wu);
  }

    public getById(body: Body): Observable<Producer>{
      return this.http.post<Producer>(this.root_url + this.addressAPI + '/' + body.topost.mail,body.authorized);
    }

    public create(body: Body): Observable<Producer>{
      return this.http.post<Producer>(this.root_url + this.addressAPI,body.topost);
    }

    public update(body: Body): Observable<Producer>{
      return this.http.put<Producer>(this.root_url + this.addressAPI + '/' + body.topost.mail,body);
    }

    public delete(body: Body): Observable<void>{
      const options = {body: body.authorized};
      return this.http.delete<void>(this.root_url + this.addressAPI + '/' + body.topost.mail,options);
    }
}
