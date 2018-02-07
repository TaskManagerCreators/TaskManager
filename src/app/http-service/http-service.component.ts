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

  getTasks(next: number) {
    return this.http.get('http://localhost:8080/tasks?next='+next).subscribe(data => this.data = data);
    //alert('data' + this.data);
  }
  getTasksByName(taskName: string , next: number){
    return this.http.get('http://localhost:8080/tasks?name='+taskName+'&next='+next).subscribe(data => this.data = data);
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
