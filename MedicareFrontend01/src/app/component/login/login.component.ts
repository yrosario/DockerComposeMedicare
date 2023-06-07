import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username = "";
  password = ""
  invalidLogin = false;
  invalidMessage = "Wrong username or password";

  constructor(private userService:UserService, private router:Router) { }

  ngOnInit(): void {
    let isLogin = this.userService.isUserLoggedIn();
    if(isLogin){
      this.redirectLoggedInUser();
    }
  }

  login(){
    let userInfo = {username:this.username, password:this.password};

    this.userService.login(userInfo).subscribe(
      res =>{
        sessionStorage.setItem("loggedIn", "1");
        sessionStorage.setItem("user", JSON.stringify(res));
        this.invalidLogin = false;
        this.router.navigate(["/shop"]);
      },
      error => {
        this.invalidLogin = true;
      }
    )
  }

  routeToRegister(){
    this.router.navigate(['register']);
  }

  redirectLoggedInUser(){
    this.router.navigate(["/shop"]);
  }

  handleLogin(form: NgForm){
    this.username = form.value.username;
    this.password = form.value.password;

    this.login();

  }
  
}
