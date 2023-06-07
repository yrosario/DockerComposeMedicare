import { Component, OnInit } from '@angular/core';
import { CartEntity } from 'src/app/entity/cart/cart-entity';
import { UserEntity } from 'src/app/entity/user/user-entity';
import { CartService } from 'src/app/service/cart.service';


@Component({
  selector: 'app-bill-summary',
  templateUrl: './bill-summary.component.html',
  styleUrls: ['./bill-summary.component.css']
})
export class BillSummaryComponent implements OnInit {

  cart:CartEntity[] = [];
  total = 0;
  userId = null;

  constructor(private cartService:CartService) { }

  ngOnInit(): void {
  
    let user:UserEntity = JSON.parse(sessionStorage.getItem("user"));
    if(user.uid){
      this.getCart(user.uid);
      this.userId = user.uid;
    }

    this.sumTotal();
    

    
  }

  sumTotal(){
    this.total = 0;
    let cart = this.cart;

    for(let item of cart){
      this.total += item.quantity * item.product.price;
    }
  }



    //Get user carts from server
  getCart(id:number){
    this.cartService.getCart(id).subscribe(
      res => {
        this.cart = res;
        this.cartService.setCart(res);
        this.removeAllItemsFromCart();
      }
    );
  }

  //Add to cart on server
  addToCart(uid:number,pid:number){
    this.cartService.addToCart(uid,pid).subscribe(
      res =>{
        this.sumTotal();
      }
    )
  }

  //update item cart to the server
  updateToCart(uid:number, cartItem:CartEntity){
    this.cartService.updateToCart(uid,cartItem).subscribe(
      res =>{
        this.sumTotal();
        this.getCart(uid);
      }
    )
  }

  //Delete item on the cart on the server
  removeFromCart(uid:number, iid:number){
    this.cartService.removeFromCart(uid,iid).subscribe(
      res=>{
        this.sumTotal();
      }
    );
  }

  //Remove all items from cart
  removeAllItemsFromCart(){
    this.cart.forEach(
      item=>{
        this.removeFromCart(this.userId,item.id);
      }
    );
  }

}
