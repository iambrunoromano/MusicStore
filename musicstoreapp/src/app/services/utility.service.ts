import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UtilityService {

  constructor() { }

  public quantityArray(length: number): Array<number> {
    if (length >= 0) {
      length-=1;
      let arr = new Array(length);
      for(let i=0;i<length;++i){
        arr[i] = i+2;
      }
      return arr;
    }
    return [];
  }

}
