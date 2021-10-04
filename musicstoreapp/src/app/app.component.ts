import { Component,OnInit } from '@angular/core';
import { Category } from './interfaces/category';
import { Product } from './interfaces/product';
import { HttpErrorResponse } from '@angular/common/http';

import { CategoryService } from './services/category.service';
import { ProductService } from './services/product.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'musicstoreapp';
  root_url = 'http://localhost:8080/musicstore/api/';

  public categories : Category[] = [];
  public category = <Category>{ };
  public products : Product[] = [];
  public product = <Product>{ };

  constructor(private categoryService : CategoryService, private productService : ProductService){
    /*categoryService.getAllCategories().subscribe(data=>{
      console.warn(data);
      this.categories = data;
    });
    productService.getAllProducts().subscribe(data=>{
      console.warn(data);
      this.products = data;
    });*/
  }

  ngOnInit() {
    this.getCategories();
    this.getProducts();
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

  getProducts(){
    this.productService.getAllProducts().subscribe(
      (response: Product[]) => {
        this.products = response;
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

  getByIdProduct(){
    this.productService.getByIdProduct().subscribe(
      (response: Product) => {
        this.product = response;
      },
      (error : HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }


}
