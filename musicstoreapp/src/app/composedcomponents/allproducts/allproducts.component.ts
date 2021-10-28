import { Component, OnInit } from '@angular/core';
import { Injectable } from '@angular/core';

import { HttpErrorResponse } from '@angular/common/http';

import { Product } from '../../interfaces/product';
import { CartToOrder } from '../../interfaces/carttoorder';
import { ProductService } from '../../services/product.service';
import { DataService } from '../../services/data.service';

import { Router } from '@angular/router';

@Injectable({
  providedIn : 'root'
})
@Component({
  selector: 'app-allproducts',
  templateUrl: './allproducts.component.html',
  styleUrls: ['./allproducts.component.css']
})
export class AllproductsComponent implements OnInit {

  public products: Product[] = [];
  public orderedProducts: CartToOrder[] = [];
  public chosencategory: String = '';

  constructor(private productService : ProductService,
              private dataService : DataService,
              private router: Router) {
    this.getProducts();
  }

  ngOnInit(): void {
    if(this.router.url.startsWith("/category")){
        this.products=[];
        this.chosencategory = this.router.url.replace('category/','');
        this.ProductsByCategory(this.chosencategory);
    }
    if(this.router.url.startsWith("/products")){
        this.getProducts();
    }
    if(this.router.url.startsWith("/order")){
        this.products=[];
        this.orderedProducts = this.dataService.getLastOrder();
        this.ProductsByOrder(this.orderedProducts);
    }
  }

  public getProducts(): void{
    this.productService.getAll().subscribe(
      (response: Product[]) => {
        this.products = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
  }

  public ProductsByCategory(id: String): void{
    this.productService.ProductsByCategory(id).subscribe(
      (response: Product[]) => {
        this.products = response;
      },
      (error : HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public ProductsByOrder(carttoorder: CartToOrder[]): void{
    for(let item of carttoorder){
      this.productService.getById(item.productId).subscribe(
        (response: Product) => {
          let orderedProduct : Product = response;
          orderedProduct.price = item.price; /*You want to know how much you spent on that products, not how much it costs now*/
          orderedProduct.quantity = item.quantity; /*You want to know how many products you ordered, not how many are in stock*/
          this.products.push(orderedProduct);
        },
        (error : HttpErrorResponse) => {
          alert(error.message);
        }
      );
    }
  }

}
