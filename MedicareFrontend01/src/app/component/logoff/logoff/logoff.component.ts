import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-logoff',
  templateUrl: './logoff.component.html',
  styleUrls: ['./logoff.component.css']
})
export class LogoffComponent implements OnInit {

  constructor(private router:Router) { }

  ngOnInit(): void {

    sessionStorage.removeItem("loggedIn");
    sessionStorage.removeItem("user");

    setTimeout(()=> {
      this.redirectPage();
    },3000);
  }

  redirectPage(){ 
    this.router.navigate(["/shop"]);

  }

}
