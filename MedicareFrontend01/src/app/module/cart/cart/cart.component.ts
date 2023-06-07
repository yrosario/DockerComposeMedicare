import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { debounce, debounceTime, delay, groupBy, mergeMap, Subject, Subscription, throttleTime } from 'rxjs';
import { CartEntity } from 'src/app/entity/cart/cart-entity';
import { ProductEntity } from 'src/app/entity/product/product-entity';
import { UserEntity } from 'src/app/entity/user/user-entity';
import { CartService } from 'src/app/service/cart.service';
import { MessengerService } from 'src/app/service/shared/messenger.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {

  cart:CartEntity[] = [];
  total = 0;
  userId = null;
  subscription:Subscription;

  constructor(private cartService:CartService, private msg:MessengerService,
        private router:Router) { }

  ngOnInit(): void {
    let user:UserEntity = JSON.parse(sessionStorage.getItem("user"));
    
    //Redirect to login page
    if(user === null){
      this.redirectToLogin();
    }

    this.getCart(user.uid);
    this.userId = user.uid;

    //Messages received from view products component
    this.subscription = this.msg.getMsg().pipe(
      throttleTime(1000)
      ).subscribe((product:ProductEntity) => {
          this.handleAddToCart(product, this.userId);
      });
    
    this.cart = this.cartService.getLocalCart();
  
    this.sumTotal();

  }

  ngOnDestroy(){
    //this.subscription.unsubscribe();
  }

  addProductToCart(product:ProductEntity){
    this.cartService.setCart(product);
  }

  sumTotal(){
    let totalPrice = 0;
    let totalQty = 0;

    for(let item of this.cart){
      totalPrice += item.quantity * item.product.price;
      totalQty += totalQty;
    }

    this.total = totalPrice;
  }

  //Reduce quantity or remove from cart
  removeItem(cartItem){
    if(cartItem.quantity > 1){
      cartItem.quantity--;
    }else{
      this.removeFromCart(this.userId,cartItem.id);
    }


    //update server
    this.updateToCart(this.userId, cartItem);
  }

  //Increases cart item quantity
  addItem(cartItem){
    cartItem.quantity++;

    //update cart item on server
    this.updateToCart(this.userId, cartItem);

    this.sumTotal();
  }

  billCheckout(){
    this.router.navigate(["/cart/bill-summary"]);
  }

  //Get user carts from server
  getCart(id:number){
    this.cartService.getCart(id).subscribe(
      res => {
        this.cart = res;
        this.sumTotal();
        
      }
    );
  }

  //Add to cart on server
  handleAddToCart(product:ProductEntity,uid:number){

    let existingCartItem:CartEntity= undefined;

    if(this.cart.length > 0){
      existingCartItem = this.cart.find(item => item.product.pid === product.pid);
    }
    
    let alreadyExistInCart = (existingCartItem != undefined);

    if(alreadyExistInCart){
      existingCartItem.quantity++;
      this.updateToCart(uid, existingCartItem);
    }else{
      this.saveToCart(uid,product.pid);
    }
  }

  saveToCart(uid:number,pid:number){
    this.cartService.addToCart(uid,pid).subscribe(
      (res : CartEntity) =>{
        this.cart.push(res);
        this.getCart(uid);
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

  //Check if product is in the cart
  isInCart(pid:number){
    for(let item of this.cart){
      if(item.product.pid == pid){
        return item;
      }
    }
    return false;

  }

  //If user has not logged in redirect to login page
  redirectToLogin(){
    this.router.navigate(["/login"]);
  }


}

