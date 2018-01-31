import { Component, OnInit } from '@angular/core';
import { HttpService } from './http.service';
import { Injectable } from '@angular/core';

@Component({
  selector: 'app-httpservice',
  templateUrl: './httpservice.component.html',
  styleUrls: ['./httpservice.component.css'],
  providers: [HttpService]
})
@Injectable()
export class HttpserviceComponent implements OnInit {
  data: Object;
  num: number;
  startDate: string;
  finishDate: string;

  ngOnInit() {
  }

  constructor(private httpService: HttpService) { }

  getTasks() {
    this.httpService.getData().subscribe(data => this.data = data);
  }

  postTasks(num: number, startDate: string, finishDate: string) {
    var start = new Date(startDate);
    var finish = new Date(finishDate)
    var step = (finish - start) / num;
    for (var i = +start; i < +finish; i = i + step) {
      var temp = new Date(i);
      var date = temp.getHours() + ':' + temp.getMinutes() + ' ' + temp.getDate() + '.' + (temp.getMonth() + 1) + '.' + temp.getFullYear();
      this.httpService.postData(date).subscribe(data => this.data = data);
    }
  }

}
