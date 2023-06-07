import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { API_URL, LOGIN, USER } from '../contants';
import { UserEntity } from '../entity/user/user-entity';

@Injectable({
  providedIn: 'root'
})
export class UserService {


  constructor(private http:HttpClient) {}

  /*Get all users from server*/
  getUsers(){
    return this.http.get<UserEntity[]>(`${API_URL}/${USER}`);
  }
  
  /*Delete user by id*/
  deleteUserById(id:number){
    return this.http.delete(`${API_URL}/${USER}/${id}`, {observe: "response", responseType:"text"});
  }

  /*save user to server */
  saveUser(user:UserEntity){
    return this.http.post(`${API_URL}/${USER}`,user);
  }


  isUserLoggedIn(){
   
    let isLogin = +sessionStorage.getItem("loggedIn");
 
    if(isLogin === 1){
     return true;
    }else{
     return false;
    }
   }

   //Authenticated user to server
   login(userInfo){
    return this.http.post(`${API_URL}/${USER}/${LOGIN}`,userInfo);
   }
}
