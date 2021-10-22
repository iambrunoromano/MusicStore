import { Component, OnInit } from '@angular/core';
import { Injectable } from '@angular/core';

import { HttpErrorResponse } from '@angular/common/http';

import { Category } from '../../interfaces/category';
import { CategoryService } from '../../services/category.service';

@Injectable({
  providedIn : 'root'
})
@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css']
})
export class CategoryComponent implements OnInit {

  public categories: Category[] = [];
  public category = <Category>{ };

  constructor(private categoryService : CategoryService) {
  this.getCategories();
  }

  ngOnInit(): void {
  }

  public getCategories(): void{
    this.categoryService.getAllCategories().subscribe(
      (response: Category[]) => {
        this.categories = response;
      },
      (error : HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public getByIdCategory(id: number): void{
    this.categoryService.getByIdCategory(id).subscribe(
      (response: Category) => {
        this.category = response;
      },
      (error : HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }
}
