import { Injectable } from '@angular/core';

import { HttpErrorResponse } from '@angular/common/http';

import { CartService } from '../services/cart.service';
import { OrderService } from '../services/order.service';

import { LogStatus } from '../interfaces/logstatus';
import { Body } from '../interfaces/body';
import { WebUser } from '../interfaces/webuser';
import { Product } from '../interfaces/product';
import { Cart } from '../interfaces/cart';
import { OrderReceipt } from '../interfaces/orderreceipt';

import { Router } from '@angular/router';

@Injectable({
  providedIn : 'root'
})
export class DataService{

  private userData = <WebUser>{ };
  private logStatus = <LogStatus>{ };
  private body = <Body>{ };
  private lastOrder = <OrderReceipt>{ };

  constructor(private cartService : CartService,
              private orderService : OrderService,
              private router : Router){
    this.initUserData();
    this.setLogStatus(false);
  }

  public getUserData(): WebUser{
    return this.userData;
  }

  public setUserData(mail: string, password: string): void{
    this.userData.mail = mail;
    this.userData.password = password;
    this.body.authorized = this.userData;
  }

  public initUserData(): void{
    this.userData.mail = '';
    this.userData.password = '';
  }

  public getLogStatus(): LogStatus{
    return this.logStatus;
  }

  public setLogStatus(status: boolean): void{
    this.logStatus.logstatus = status;
  }

  public setToPostBody(obj: any): Body{
    this.body.topost = obj;
    return this.body;
  }

  public addtocart(product: Product): void{
    let body : Body = {authorized : this.userData,
                       topost : product}
    this.cartService.create(body).subscribe(
      (response: Cart) => {},
      (error : HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public gotoorder(): void{
    this.orderService.create(this.userData).subscribe(
      (response: OrderReceipt) => {
        this.lastOrder = response;
        this.router.navigate(['/order']);
      },
      (error : HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public getLastOrder(): OrderReceipt{
    return this.lastOrder;
  }

}
