import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'musicstoreapp';
  root_url = 'http://localhost:8080/musicstore/api/';
  customers:any;
  constructor(private http: HttpClient){}
  getCustomers(){
    this.customers = this.http.get(this.root_url + 'customer');
  }
}
