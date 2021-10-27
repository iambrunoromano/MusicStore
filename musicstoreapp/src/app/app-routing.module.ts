import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AllproductsComponent } from './composedcomponents/allproducts/allproducts.component';
import { AllcategoriesComponent } from './composedcomponents/allcategories/allcategories.component';
import { SearchComponent } from './composedcomponents/search/search.component';
import { BestComponent } from './composedcomponents/best/best.component';
import { ProducerComponent } from './composedcomponents/producer/producer.component';

import { HomeComponent } from './home/home.component';
import { LoginComponent } from './simplecomponents/login/login.component';
import { RegisterComponent } from './simplecomponents/register/register.component';

const routes: Routes = [
  {path: '', component: BestComponent},
  {path: 'products', component: AllproductsComponent},
  {path: 'categories', component: AllcategoriesComponent},
  {path: 'search', component: SearchComponent},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'producer/:mail', component: ProducerComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
