import { Component, OnInit } from '@angular/core';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

import { HttpErrorResponse } from '@angular/common/http';

import { LogStatus } from '../../interfaces/logstatus';
import { WebUser } from '../../interfaces/webuser';
import { Customer } from '../../interfaces/customer';
import { Producer } from '../../interfaces/producer';
import { WebUserService } from '../../services/webuser.service';
import { CustomerService } from '../../services/customer.service';
import { ProducerService } from '../../services/producer.service';
import { DataService } from '../../services/data.service';

@Injectable({
  providedIn : 'root'
})
@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  public logstatus = <LogStatus>{ };

  constructor(private webuserService : WebUserService,
              private customerService : CustomerService,
              private producerService : ProducerService,
              private dataService : DataService,
              private router : Router) { }

  ngOnInit(): void {
  }

  public register(): void{

    let mail : string = (<HTMLInputElement>document.getElementById("username"))?.value;
    let password : string = (<HTMLInputElement>document.getElementById("password"))?.value;
    let imgurl : string = (<HTMLInputElement>document.getElementById("imgurl"))?.value;
    let name : string = (<HTMLInputElement>document.getElementById("name"))?.value;
    let surname : string = (<HTMLInputElement>document.getElementById("surname"))?.value;
    let paymentCard : string = (<HTMLInputElement>document.getElementById("cardnumb"))?.value;
    let address : string = (<HTMLInputElement>document.getElementById("imgurl"))?.value;

    let wu : WebUser = {mail: mail,
                        password: password,
                        imgurl: imgurl};
    let selectedOption : string = (<HTMLSelectElement>document.getElementById("customer-producer-selector"))?.value;
    this.webuserService.create(wu).subscribe(
      (response: WebUser) => {
        this.dataService.setUserData(wu.mail, wu.password);
        this.dataService.setLogStatus(true);
        if(selectedOption=="customer"){
          let customer : Customer = {
            mail : mail,
            name : name,
            surname : surname,
            paymentCard : paymentCard,
            billingAddress : address
          };
          this.customerService.create(customer).subscribe(
            (response: Customer) => {
              this.router.navigate(['/']);
            },
            (error : HttpErrorResponse) => {
              alert(error.message);
              this.router.navigate(['/login']);
            });
        };
        if(selectedOption=="producer"){
          let producer : Producer = {
            mail : mail,
            name : name,
            address : address
          };
          this.producerService.create(producer).subscribe(
            (response: Producer) => {
              this.router.navigate(['/']);
            },
            (error : HttpErrorResponse) => {
              alert(error.message);
              this.router.navigate(['/login']);
            });
        };
      },
      (error : HttpErrorResponse) => {
        alert(error.message);
        this.router.navigate(['/login']);
      }
    );
  }

}
