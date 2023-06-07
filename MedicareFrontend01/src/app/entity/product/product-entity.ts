import { CategoryEntity } from "../category/category-entity";

export class ProductEntity {
    pid:number;
    name:string;
    brand:string;
    price:number;
    active:boolean;
    numberOfViews:number;
    imageBlob:Blob;
    category:CategoryEntity;
    qty:number;
    imgUrl:string;

    constructor(pid?:number, name?:string, category?:CategoryEntity, price?:number, qty?:number,imgUrl?:string){

        this.pid = pid;
        this.name = name;
        this.category = category;
        this.price = price;
        this.qty = qty;
        this.imgUrl=imgUrl;

    }
}
