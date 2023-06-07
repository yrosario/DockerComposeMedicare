import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router, UrlSegment } from '@angular/router';
import { ProductEntity } from 'src/app/entity/product/product-entity';
import { RoleEntity } from 'src/app/entity/role/role-entity';
import { UserEntity } from 'src/app/entity/user/user-entity';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  user:UserEntity = new UserEntity();

  //User registration success message
  isRegister = null;
  registerMsg = "";

  //Password match message
  isPassMatch = true;
  passMatchMsg = "Password doesn't match";

  constructor(private userService:UserService, private router:Router) { }

  ngOnInit(): void {
  }

  registerUser(form: NgForm){
    const value = form.value;

    let user:UserEntity = new UserEntity();

    user.firstname = value.firstname;
    user.lastname = value.lastname;
    user.username = value.username;
    user.email = value.email;
    user.password = value.password;
    user.role = new RoleEntity(1,"User");
  
    if(user.password == value.retyPassword){
        this.saveUser(user);
        this.isPassMatch = true;
    }else{
      this.isPassMatch = false;
    }

  }

  saveUser(user:UserEntity){
    this.userService.saveUser(user).subscribe(
      res =>{
        this.user = user;
        this.isRegister = true;
        this.registerMsg = "User registration successful!. Redirecting to login page...";
        setTimeout(()=> {
          this.router.navigate(["/login"]);
        },5000);
        
      },
      error=>{
        this.isRegister = false;
        this.registerMsg = "The was an error with form. Review the form!";
      }
    )
  }



}
