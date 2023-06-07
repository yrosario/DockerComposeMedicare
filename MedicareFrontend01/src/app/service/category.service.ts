import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { API_URL, CATEGORY } from '../contants';
import { CategoryEntity } from '../entity/category/category-entity';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  catogries = [new CategoryEntity(1,"Vitamin"),
              new CategoryEntity(2,"Pain Relief"),
              new CategoryEntity(3,"Cold/Flu"),
              new CategoryEntity(4,"Nasal Congestion Relief"),
              new CategoryEntity(5,"Antibiotics")]

  constructor(private httpClient:HttpClient) { }

  /* Retrieves all categories from REST server*/
  getCategories(){
    return this.httpClient.get<CategoryEntity[]>(API_URL+CATEGORY);
  }

  /* Retrieve a specific category from REST server */
  getCategoryById(id:number){
    return this.httpClient.get<CategoryEntity>(API_URL+CATEGORY+`/${id}`);
  }

  /* Post category to REST api */
  saveCategory(category:CategoryEntity){
    this.httpClient.post(API_URL+category,category);
  }

  /*Put category to REST api */
  updateCategory(category:CategoryEntity){
    this.httpClient.put(API_URL+CATEGORY,category);
  }

  /*Delete category base on id from REST api*/
  deleteCategoryById(id:number){
    return this.httpClient.delete(API_URL+CATEGORY+`/${id}`, {observe: 'response', responseType: "json"});
  }

}
