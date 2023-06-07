import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminComponent } from './component/admin/admin.component';
import { EditProductComponent } from './component/admin/manage-product/edit-product/edit-product.component';
import { ManageProductComponent } from './component/admin/manage-product/manage-product.component';
import { BillSummaryComponent } from './module/cart/cart/bill-summary/bill-summary.component';
import { CartComponent } from './module/cart/cart/cart.component';
import { LoginComponent } from './component/login/login.component';
import { LogoffComponent } from './component/logoff/logoff/logoff.component';
import { RegisterComponent } from './component/register/register.component';
import { ProductItemComponent } from './component/view-products/product-item/product-item/product-item.component';
import { ViewProductsComponent } from './component/view-products/view-products.component';

const routes: Routes = [
  {path: '', component:ViewProductsComponent},
  {path: 'register', component:RegisterComponent},
  {path: 'login', component:LoginComponent},
  {path: 'shop', component:ViewProductsComponent},
  {path: 'shop/product/:id', component: ProductItemComponent},
  {path: 'cart', component: CartComponent},
  {path: 'cart/bill-summary', component: BillSummaryComponent},
  {path: 'admin', component: ManageProductComponent},
  {path: 'admin/manage-products', component:ManageProductComponent},
  {path: 'admin/manage-products/edit-product', component:EditProductComponent},
  {path: 'admin/manage-products/edit-product/:id', component:EditProductComponent},
  {path: 'logoff', component:LogoffComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
