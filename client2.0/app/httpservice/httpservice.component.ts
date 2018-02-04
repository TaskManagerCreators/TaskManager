import { Component, OnInit } from '@angular/core';
import { HttpService } from '../http.service';
import { Injectable } from '@angular/core';
@Component({
  selector: 'app-httpservice',
  templateUrl: './httpservice.component.html',
  styleUrls: ['./httpservice.component.css'],
  providers: [HttpService]
})
export class HttpserviceComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

}
