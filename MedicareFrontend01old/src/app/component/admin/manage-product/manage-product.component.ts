import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ProductEntity } from 'src/app/entity/product/product-entity';
import { ImageService } from 'src/app/service/image.service';
import { ProductService } from 'src/app/service/product.service';


@Component({
  selector: 'app-manage-product',
  templateUrl: './manage-product.component.html',
  styleUrls: ['./manage-product.component.css']
})
export class ManageProductComponent implements OnInit {

  products:ProductEntity[]= [];
  images:{pid:string, content:any}[] = [];
  searchTerm = "";
  
  constructor(private productService:ProductService, private router:Router, private imageService:ImageService ) { }

  ngOnInit(): void {
    this.getProducts();
  }

  getProducts(){
    this.productService.getProducts().subscribe(
      res => {
        this.products = res;
        this.loadImages();
      }
    )
  }

  deleteProductById(id:number){
    this.productService.deleteProductById(id).subscribe(
      res => {
        //Refresh product list
        this.getProducts();
      }
    )
  }

  navigateProductEdit(id?:number){
    if(id === undefined){
        this.router.navigate([`admin/manage-products/edit-product`]);
    }else{
      this.router.navigate([`admin/manage-products/edit-product/${id}`]);
    }
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

  disableProduct(product:ProductEntity){
    product.active = !product.active;
    this.productService.updateProduct(product).subscribe();
  }

}
