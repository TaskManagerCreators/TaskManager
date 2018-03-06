import { Component, OnInit } from '@angular/core';
import { UUID } from 'angular2-uuid';
import { MatPaginator, MatTableDataSource } from '@angular/material';
import { HttpClient, HttpHeaders } from '@angular/common/http';


@Component({
  selector: 'app-errors',
  templateUrl: './errors.component.html',
  styleUrls: ['./errors.component.css']
})
export class ErrorsComponent implements OnInit {
  ELEMENT_DATA: Element[] = [

  ];

  const task_manager_port = 44444;
  const user_manager_port = 55555;

  displayedColumns = ['id', 'exceptionClass', 'stackTrace', 'date'];
  dataSource = new MatTableDataSource<Error>(this.ELEMENT_DATA);

  constructor(private http: HttpClient) {
  }

  ngOnInit() {
    this.getErrorRecords();
  }

  getErrorRecords(){
    this.http.get('http://localhost:'+ this.task_manager_port + '/tasks/error_records').subscribe(data => {
      this.parse(data);
    });

  }

  parse(data) {
    //var data_size = data[0];
    //data = Object.values(data[1]);
    //data = JSON.stringify(data);

    Object.values(data).forEach(item => {
      this.ELEMENT_DATA.push({
        id: item.id,
        exceptionClass: item.exceptionClass,
        message : item.message,
        stackTrace : item.stackTrace,
        date: item.date,//moment(item.date).format('hh:mm DD.MM.YYYY'),
      });
    });
    /*if (data_size % this.size == 0) {
      this.pages = parseInt(data_size / this.size);
    }
    else {
      this.pages = parseInt(data_size / this.size) + 1;
    }*/
    this.dataSource = new MatTableDataSource<Error>(this.ELEMENT_DATA);
  }



}

export interface Error{
  id : UUID;
  exceptionClass : string;
  message : string;
  stackTrace : string;
  date : Date;
}
