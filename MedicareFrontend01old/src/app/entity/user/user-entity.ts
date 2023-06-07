import { ProductEntity } from "../product/product-entity";
import { RoleEntity } from "../role/role-entity";

export class UserEntity {
    uid?:number;
    firstname:string;
    lastname:string;
    email:string;
    username:string;
    password:string;
    products:[ProductEntity];
    role:RoleEntity;
}
