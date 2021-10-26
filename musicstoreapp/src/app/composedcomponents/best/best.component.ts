import { Component, OnInit } from '@angular/core';
import { Injectable } from '@angular/core';

import { HttpErrorResponse } from '@angular/common/http';

import { Product } from '../../interfaces/product';
import { ProductService } from '../../services/product.service';
import { Producer } from '../../interfaces/producer';
import { ProducerService } from '../../services/producer.service';

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
  public producers: Producer[] = [];

  constructor(private productService : ProductService,
              private producerService : ProducerService) {
              this.getBestProducts();
              this.getBestProducers();
  }

  ngOnInit(): void {
  }

  public getBestProducts(): void{
    this.productService.bestProducts().subscribe(
      (response: Product[]) => {
        this.products = response;
        console.log(response);
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
        console.log(response);
      },
      (error : HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

}
