import { Component,OnInit } from '@angular/core';
import { Category } from './interfaces/category';
import { Product } from './interfaces/product';
import { HttpErrorResponse } from '@angular/common/http';

import { Inject } from '@angular/core';
import { DOCUMENT } from '@angular/common';

import { CategoryService } from './services/category.service';
import { Dropdown } from '../scripts/dropdown';

import { ProductComponent } from './product/product.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  
  public dropdown = new Dropdown();

  constructor(@Inject(DOCUMENT) private document: Document){}

  ngOnInit() {
  }

  doDropdown(){
    this.document = this.dropdown.funAlert(document);
  }

}
