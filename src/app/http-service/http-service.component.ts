import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UUID } from 'angular2-uuid';


@Component({
  selector: 'app-http-service',
  templateUrl: './http-service.component.html',
  styleUrls: ['./http-service.component.css']
})
@Injectable()
export class HttpServiceComponent implements OnInit {

  data: Object;

  constructor(private http: HttpClient) { }

  getTasks() {
    return this.http.get('http://localhost:8080/tasks');
    //alert('data' + this.data);
  }

  deleteTasks() {
    return this.http.get('http://localhost:8080/tasks/*').subscribe(data => this.data = data);
  }

  deleteTask(id: UUID) {
    return this.http.delete('http://localhost:8080/tasks?id=' + id).subscribe(data => this.data = data);
  }

  ngOnInit() {
  }

}
