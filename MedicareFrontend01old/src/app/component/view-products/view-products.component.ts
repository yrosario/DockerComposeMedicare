import { Component, OnInit } from '@angular/core';
import { CategoryEntity } from 'src/app/entity/category/category-entity';
import { ProductEntity } from 'src/app/entity/product/product-entity';
import { CategoryService } from 'src/app/service/category.service';
import { ImageService } from 'src/app/service/image.service';
import { ProductService } from 'src/app/service/product.service';

@Component({
  selector: 'app-view-products',
  templateUrl: './view-products.component.html',
  styleUrls: ['./view-products.component.css']
})
export class ViewProductsComponent implements OnInit {

  products:ProductEntity[] = [];
  categories:CategoryEntity[] = [];
  images:{pid:string, content:any}[] = [];
  pageTitle = "Most Viewed Items";

  //values use to filter
  oper = "";
  value = "";

  constructor(private productService:ProductService, private categoryService:CategoryService,private imageService:ImageService) { }

  ngOnInit(): void {
    this.getProducts();
    this.getCategories()
  }

  getProducts(){
    this.productService.getProducts().subscribe(
      res => {
        this.products = res;
        this.loadImages();
      }
    )
  }

  getCategories(){
    this.categoryService.getCategories().subscribe(
      res =>{
        this.categories = res;
      }
    );
  }

  loadImages(){
    this.products.forEach(product => {
      this.imageService.getImage(product.pid).subscribe(
        res =>
        {
          this.createImageFromBlob(res, String(product.pid));
        }
      );
    });
  }

  createImageFromBlob(image: Blob, pid:string){
    let reader = new FileReader();
    reader.addEventListener("load", () =>{
      this.images.push({pid:pid, content:reader.result});
    }, false);

    if(image){
      reader.readAsDataURL(image);
    }
  }

  findImage(id){
    for(let img of this.images){
      if(img.pid == id){
        return img.content;
      }
    }

    return null;
  }

  setTitle(itemName){
    if(itemName === '')
    {
      this.pageTitle = "All Products";
    }else{
      this.pageTitle = "Search Result For " + itemName;
    }
  }

}
