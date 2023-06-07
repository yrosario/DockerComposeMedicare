import { Injectable } from '@angular/core';
import { CategoryEntity } from '../entity/category/category-entity';
import { ProductEntity } from '../entity/product/product-entity';
import { HttpClient, HttpHeaders} from '@angular/common/http';
import { API_URL, PRODUCT } from '../contants';
import { map } from 'rxjs/operators'

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  products:[ProductEntity];
  options:{};

  constructor(private httpClient:  HttpClient) { 
    this.options = { 
        headers: new HttpHeaders({'Content-Type': 'application/json'})
      };
   }

   /*Retrieves all products from http://localhost:8080/api/v1/product */
  getProducts(){
    return this.httpClient.get<[ProductEntity]>(API_URL + PRODUCT);
  }

  /*Retrieves a specific product base on product id from http://localhost:8080/api/v1/product/:id */
  getProductById(id:number){
    return this.httpClient.get<ProductEntity>(API_URL+PRODUCT+"/" +id);
  }

  /* Deletes a specific product base on id from http://localhost:8080/api/v1/product/:id */
  deleteProductById(id:number){
    return this.httpClient.delete(API_URL+PRODUCT+"/"+id, {observe: "response", responseType:"text"});
  }

  /* Post a new product to REST api */
  saveProduct(product:ProductEntity){
    let test = { 
      headers: new HttpHeaders({'Content-Type': 'application/json'})};

    return this.httpClient.post(API_URL+`/${PRODUCT}`,product,test);
  }

  /* Put a product to REST api */
  updateProduct(product:ProductEntity){
    let test = { 
      headers: new HttpHeaders({'Content-Type': 'application/json'})};
    return this.httpClient.put(API_URL +`/${PRODUCT}`,product);
  }

   findProductById(id:number){
    for(let product of this.products){
      if(product.pid === id){
        return product;     
       }
    }
    return null;
   }


   save(product){
    for(let i = 0; i < this.products.length; i++){
      if(product.id === this.products[i].pid)
      {
        this.products[i] = product;
        return;
      }
    }

    this.products.push(product);
   }
   
}
