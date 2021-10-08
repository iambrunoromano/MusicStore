import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';


@Injectable({
  providedIn : 'root'
})
export class Dropdown{
  constructor(){}

  public funAlert(document: Document): Document{
    alert('myFunction works');
    var object = document.getElementById("trigger");
    if(object!==null){
      object.classList.toggle("show");
    }
    else{
      alert("An error occurred!")
    }
    return document;
  }
}

// Close the dropdown if the user clicks outside of it
/*window.onclick = function(event) {
  if (!event.target.matches('.dropbtn')) {
    var dropdowns = document.getElementsByClassName("dropdown-content");
    var i;
    for (i = 0; i < dropdowns.length; i++) {
      var openDropdown = dropdowns[i];
      if (openDropdown.classList.contains('show')) {
        openDropdown.classList.remove('show');
      }
    }
  }
}*/
