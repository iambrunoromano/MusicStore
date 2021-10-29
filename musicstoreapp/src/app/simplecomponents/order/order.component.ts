import { Component, OnInit } from '@angular/core';
import { Injectable } from '@angular/core';

import { HttpErrorResponse } from '@angular/common/http';

import { Product } from '../../interfaces/product';
import { OrderReceipt } from '../../interfaces/orderreceipt';
import { CartToOrder } from '../../interfaces/carttoorder';

import { ProductService } from '../../services/product.service';
import { DataService } from '../../services/data.service';


@Injectable({
  providedIn : 'root'
})
@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css']
})
export class OrderComponent implements OnInit {

  public products: Product[] = [];
  public orderedProducts: CartToOrder[] = [];
  public lastOrder = <OrderReceipt>{};

  constructor(private productService : ProductService,
              private dataService : DataService) { }

  ngOnInit(): void {
    this.init();
  }

  private init(): void{
    this.lastOrder = this.dataService.getLastOrder();
    this.orderedProducts = this.lastOrder.boughtitems;
    this.ProductsByOrder(this.orderedProducts);
  }

  public ProductsByOrder(carttoorder: CartToOrder[]): void{
    for (const [ind, item] of Object.entries(carttoorder)){
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
