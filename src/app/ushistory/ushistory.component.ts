import { Component, OnInit } from '@angular/core';
import { UUID } from 'angular2-uuid';
import { MatPaginator, MatTableDataSource } from '@angular/material';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-ushistory',
  templateUrl: './ushistory.component.html',
  styleUrls: ['./ushistory.component.css']
})
export class UshistoryComponent implements OnInit {

  ELEMENT_DATA: Element[] = [

  ];

  const task_manager_port = 44444;
  const user_manager_port = 55555;

  displayedColumns = ['eventDate', 'task_id', 'user' ,  'event'];
  dataSource = new MatTableDataSource<History>(this.ELEMENT_DATA);

  constructor(private http: HttpClient) {
  }

  ngOnInit() {
    this.getHistory();
  }

  getHistory(){
    this.http.get('http://localhost:' + this.task_manager_port + '/tasks/history').subscribe(data => {
      this.parse(data);
    });

  }

  parse(data) {
    //var data_size = data[0];
    //data = Object.values(data[1]);
    //data = JSON.stringify(data);

    Object.values(data).forEach(item => {
      this.ELEMENT_DATA.push({
        eventDate: item.eventDate,
        task_id : item.currentTask.id,
        user: item.currentTask.user,
        event: item.event,//moment(item.date).format('hh:mm DD.MM.YYYY'),
      });
    });
    /*if (data_size % this.size == 0) {
      this.pages = parseInt(data_size / this.size);
    }
    else {
      this.pages = parseInt(data_size / this.size) + 1;
    }*/
    this.dataSource = new MatTableDataSource<History>(this.ELEMENT_DATA);
  }

}

export interface History{
  eventDate : Date;
  task_id : UUID;
  user : string;
  event : string;
}

export interface Task {
  id: UUID;
  name: string;
  describe: string;
  targetTime: Date;
  contacts: string;
  reaction: Reaction;
  status: string;
}
