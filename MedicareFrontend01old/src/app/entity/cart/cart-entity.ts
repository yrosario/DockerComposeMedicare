import { ProductEntity } from "../product/product-entity";

export class CartEntity
 {
    id:number;
    quantity:number;
    product:ProductEntity;
}
