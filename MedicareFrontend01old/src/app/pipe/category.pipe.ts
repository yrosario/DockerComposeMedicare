import { Pipe, PipeTransform } from '@angular/core';
import { __values } from 'tslib';
import { ProductEntity } from '../entity/product/product-entity';

@Pipe({
  name: 'category'
})
export class CategoryPipe implements PipeTransform {

  transform(products: ProductEntity[], oper:string, value:string): ProductEntity[] {
    if(oper.toLowerCase() === "search"){
      return this.searchFilter(products,value);
    }else if(oper.toLocaleLowerCase() == "category"){ 
      return this.categoryFilter(products,value);
    }
    

    return products;
  }

  categoryFilter(products: ProductEntity[], filterText:string):ProductEntity[]{
    if(products.length === 0){
      return products;
    }else if(filterText === '')
    {
      return products.slice(0,10);
    }else{
      return products.filter(product =>{
        return product.category.name === filterText;
      });
    }
  }

  searchFilter(products: ProductEntity[], filterText:string):ProductEntity[]{
    if(products.length === 0 || filterText === ""){
      return products;
    }else{
      return products.filter(product =>{

        let productText = product.name.toLowerCase();
        let searchTerms = filterText.split(" ");
        for(let term of searchTerms){
          let regex = new RegExp(term.toLowerCase(), 'gi');
          
          if(regex.test(productText))
          {
            return true;
          };
        }
        return false;
      });
    }
  }

}
