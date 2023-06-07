import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class RoleService {

  roles = [{id:1, name:"user"},
          {id:2, name:"admin"}];

  constructor() { }

  getRoles(){
    return this.roles;
  }


}
