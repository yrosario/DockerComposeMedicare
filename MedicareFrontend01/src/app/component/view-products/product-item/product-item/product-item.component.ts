import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductEntity } from 'src/app/entity/product/product-entity';
import { ImageService } from 'src/app/service/image.service';
import { ProductService } from 'src/app/service/product.service';
import { MessengerService } from 'src/app/service/shared/messenger.service';
import { UserService } from 'src/app/service/user.service';
import { debounceTime, Observable, fromEvent } from 'rxjs';


@Component({
  selector: 'app-product-item',
  templateUrl: './product-item.component.html',
  styleUrls: ['./product-item.component.css']
})
export class ProductItemComponent implements OnInit {

  @Input() product:ProductEntity;
  @Input() image:any = null;
  btnDisable = false;
  addToCartBtn = document.getElementById("prod-cart-btn");
  isDisable = false;

  constructor(private activedRoute:ActivatedRoute, private productService:ProductService,private msg:MessengerService,
              private userService:UserService, private router:Router, private imageService:ImageService) { }

  ngOnInit(): void {
  
      let paramValue = +this.activedRoute.snapshot.paramMap.get("id");

    if(paramValue){
      this.getProductById(paramValue);
    }

  
  }


  getProductById(id:number){
    this.productService.getProductById(id).subscribe(
      res=>{
        this.product = res;
        this.loadImage();
      }
    )
  }

  addToCart(){

    let isLogin = this.userService.isUserLoggedIn();
    if(!isLogin){
      this.redirectLogin();
    }else{

      this.msg.sendMsg(this.product);
      this.isDisable = true;

    }
  }

  goToCart(){
    let isLogin = this.userService.isUserLoggedIn();
    if(!isLogin){
      this.redirectLogin();
    }else{
      this.router.navigate(["/cart"]);
    }
  }

  redirectLogin(){
    this.router.navigate(["login"]);
  }


  loadImage(){
    this.imageService.getImage(this.product.pid).subscribe(
        res =>
        {
          this.createImageFromBlob(res, String(this.product.pid));
        }
      );
  }

  createImageFromBlob(image: Blob, pid:string){
    let reader = new FileReader();
    reader.addEventListener("load", () =>{
      this.image = reader.result;
    }, false);

    if(image){
      reader.readAsDataURL(image);
    }
  }


 



}
