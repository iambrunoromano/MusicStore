import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AllproductsComponent } from './composedcomponents/allproducts/allproducts.component';
import { AllcategoriesComponent } from './composedcomponents/allcategories/allcategories.component';
import { SearchComponent } from './composedcomponents/search/search.component';

import { HomeComponent } from './home/home.component';
import { LoginComponent } from './simplecomponents/login/login.component';
import { RegisterComponent } from './simplecomponents/register/register.component';

const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    children: [
      {path: 'products', component: AllproductsComponent},
      {path: 'categories', component: AllcategoriesComponent},
      {path: 'search', component: SearchComponent},
    ]},
    { path: 'login', component: LoginComponent},
    { path: 'register', component: RegisterComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
