import { Component } from '@angular/core';
import { Category } from './interfaces/category';
import { HttpErrorResponse } from '@angular/common/http';

import { CategoryService } from './services/category.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent{
  title = 'musicstoreapp';
  root_url = 'http://localhost:8080/musicstore/api/';
  public categories : Category[];
  public category = {} as Category;

  constructor(private categoryService : CategoryService){
    this.categories = [];
  }

  getCategories(){
    this.categoryService.getAllCategories().subscribe(
      (response: Category[]) => {
        this.categories = response;
      },
      (error : HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  getByIdCategory(){
    this.categoryService.getByIdCategory().subscribe(
      (response: Category) => {
        this.category = response;
      },
      (error : HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }
}
