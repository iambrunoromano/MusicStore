import { Component,OnInit } from '@angular/core';

import { Inject } from '@angular/core';
import { DOCUMENT } from '@angular/common';

import { Dropdown } from '../scripts/dropdown';


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
