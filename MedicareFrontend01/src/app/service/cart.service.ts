import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { API_URL, CART, CART_ITEM, USER } from '../contants';
import { CartEntity } from '../entity/cart/cart-entity';
import { ProductEntity } from '../entity/product/product-entity';
import { find, tap } from 'rxjs/operators';
import { computeMsgId } from '@angular/compiler';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  cart:CartEntity[]=[];
  totalPrice = 0;
  totalQuantity = 0;

  storage: Storage = sessionStorage;

  constructor(private http:HttpClient) {
    let data = JSON.parse(this.storage.getItem('cartItems'));

    if(data != null){
      this.cart = data;
    }

    this.computeCartTotal();
  }

  //Check if item is in the cart
  getItemInCart(pid:number){
    let existingCartItem:CartEntity= undefined;


    console.log(JSON.stringify( this.cart) + " " +  this.cart.length);
    if(this.cart.length > 0){
        existingCartItem = this.cart.find(item => {console.log(item);item.product.pid;} );
    }

    return existingCartItem
  }

  //Handles adding to cart. If cart already in the cart increases
  //the quantity performs put operation. Otherwise, it does a
  //post operation.
  handleAddToCart(product:ProductEntity, uid:number){

    let cartItem = this.getItemInCart(product.pid);
    let alreadyExistInCart = (cartItem != undefined);
    
    if(alreadyExistInCart){
      cartItem.quantity++;
      this.updateToCart(uid, cartItem);
    }else{
      //this.cart.push(cartItem);
      this.addToCart(uid,product.pid);
    }
  
    this.computeCartTotal();
  }


  //Computes cart total
  computeCartTotal(){
    let totalPrice = 0;
    let totalQty = 0;

    console.log(this.cart);
    for(let item of this.cart){
      totalPrice += item.quantity * item.product.price;
      totalQty += totalQty;
    }

    this.totalPrice = totalPrice;
    this.totalQuantity = totalQty;
  }

  persistantCartItems(){
    this.storage.setItem('cartItems', JSON.stringify(this.cart));
  }

  // Get cart from server
  getCart(id:number): Observable<CartEntity[]>{
    return this.http.get<CartEntity[]>(`${API_URL}/${CART}/${USER}/${id}`)
    .pipe(
      map(
        res=>{
          return res;
        }
      )
    );
  }

  

  //Post item to cart on server
  addToCart(uid:number, pid:number):Observable<CartEntity>{
    let bodyParam = {productId:pid};
    return this.http.post<CartEntity>(`${API_URL}/${CART}/${USER}/${uid}`,bodyParam);
  }

  //Update cart item on server
  updateToCart(uid:number,cartItem:CartEntity):Observable<CartEntity>{
    return this.http.put<CartEntity>(`${API_URL}/${CART}/${USER}/${uid}`,cartItem);
  }

  //Remove item from cart on the server
  removeFromCart(uid:number, iid:number){
    return this.http.delete(`${API_URL}/${CART}/${USER}/${uid}/${CART_ITEM}/${iid}`,{observe: "response", responseType:"text"});
  }


  setCart(cart){
    this.cart = cart;
  }

  getLocalCart(){
    return this.cart;
  }

  isInCart(pid:number){
    for(let item of this.cart){
      if(item.product.pid == pid){
        return item;
      }
    }
    return undefined;

  }
}
