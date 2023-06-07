import { Injectable } from '@angular/core';
import { Subject } from 'rxjs'
import { ProductEntity } from 'src/app/entity/product/product-entity';

@Injectable({
  providedIn: 'root'
})
export class MessengerService {

  subject = new Subject<any>();

  constructor() { }

  //sendMsg will be call from product item
  sendMsg(product: ProductEntity){
    this.subject.next(product);
  }

  cleared(){
    this.subject.next('');
  }

  getMsg(){
    return this.subject.asObservable();
  }
}
