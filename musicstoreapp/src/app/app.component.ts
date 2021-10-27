import { Component,OnInit } from '@angular/core';

import { LogStatus } from './interfaces/logstatus';
import { WebUser } from './interfaces/webuser';

import { DataService } from './services/data.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{

  public userData = <WebUser>{ };
  public logStatus = <LogStatus>{ };

  constructor(private dataService: DataService){}

  ngOnInit() {
    this.userData = this.dataService.getUserData();
    this.logStatus = this.dataService.getLogStatus();
  }
}
