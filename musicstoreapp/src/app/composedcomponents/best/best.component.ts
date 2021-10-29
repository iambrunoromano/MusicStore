import { Component, OnInit } from '@angular/core';
import { Injectable } from '@angular/core';

import { HttpErrorResponse } from '@angular/common/http';

import { WebUser } from '../../interfaces/webuser';
import { Product } from '../../interfaces/product';
import { ProductService } from '../../services/product.service';
import { Producer } from '../../interfaces/producer';
import { ProducerService } from '../../services/producer.service';
import { CartService } from '../../services/cart.service';
import { DataService } from '../../services/data.service';
import { UtilityService } from '../../services/utility.service';

import { Router } from '@angular/router';

@Injectable({
  providedIn : 'root'
})
@Component({
  selector: 'app-best',
  templateUrl: './best.component.html',
  styleUrls: ['./best.component.css']
})
export class BestComponent implements OnInit {

  public products: Product[] = [];
  public productsCart: Product[] = [];
  public producers: Producer[] = [];
  public logged: boolean;

  constructor(private productService : ProductService,
              private producerService : ProducerService,
              private cartService : CartService,
              private dataService : DataService,
              private utilityService : UtilityService,
              private router : Router) {
              this.getBestProducts();
              this.getBestProducers();
              this.logged = false;
  }

  ngOnInit(): void {
    this.logged = this.dataService.getLogStatus().logstatus;
    if(this.logged){
      this.getCart(this.dataService.getUserData());
    }
  }

  public quantityArray(length: number): Array<number>{
    return this.utilityService.quantityArray(length);
  }

  public visitstore(mail: String): void{
    this.router.navigate(['/producer/' + mail]);
  }

  public getBestProducts(): void{
    this.productService.bestProducts().subscribe(
      (response: Product[]) => {
        this.products = response;
      },
      (error : HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public getBestProducers(): void{
    this.producerService.bestProducers().subscribe(
      (response: Producer[]) => {
        this.producers = response;
      },
      (error : HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public getCart(wu: WebUser): void{
    this.cartService.ProductsByCart(wu).subscribe(
      (response: Product[]) => {
        this.productsCart = response;
      },
      (error : HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public addtocart(product: Product): void{
    product.quantity = <number><unknown>(<HTMLSelectElement>document.getElementById("quantity-selector"))?.value;
    this.dataService.addtocart(product);
    this.getCart(this.dataService.getUserData());
    this.router.navigate(['']);
  }

  public gotoorder(): void{
    this.dataService.gotoorder();
  }

}
