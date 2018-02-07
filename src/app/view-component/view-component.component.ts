import { Component, OnInit, ViewChild, Inject } from '@angular/core';
import { MatPaginator, MatTableDataSource } from '@angular/material';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA, MatCheckboxModule } from '@angular/material';
import { HttpServiceComponent } from 'app/http-service/http-service.component';
import { MatDatepickerInputEvent } from '@angular/material/datepicker';
import { UUID } from 'angular2-uuid';
import { FormControl } from '@angular/forms';
import { MomentDateAdapter } from '@angular/material-moment-adapter';
import { DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE } from '@angular/material/core';
import { SelectionModel } from '@angular/cdk/collections';
import { MatSnackBar } from '@angular/material/snack-bar';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { OwlDateTimeModule, OwlNativeDateTimeModule } from 'ng-pick-datetime';




import * as _moment from 'moment';
const moment = _moment;

// See the Moment.js docs for the meaning of these formats:
// https://momentjs.com/docs/#/displaying/format/
export const MY_FORMATS = {
  parse: {
    dateInput: 'LL',
  },
  display: {
    dateInput: 'LL',
    monthYearLabel: 'MMM YYYY',
    dateA11yLabel: 'LL',
    monthYearA11yLabel: 'MMMM YYYY',
  },
};


@Component({
  selector: 'app-view-component',
  templateUrl: './view-component.component.html',
  styleUrls: ['./view-component.component.css'],
  providers: [
    {
      provide: DateAdapter,
      useClass: MomentDateAdapter,
      deps: [MAT_DATE_LOCALE]
    },
    {
      provide: MAT_DATE_FORMATS,
      useValue: MY_FORMATS
    },
  ]
})
export class ViewComponentComponent implements OnInit {

  ELEMENT_DATA: Element[] = [

  ];

  //data: Object;
  event: string;
  count = 0;
  displayedColumns = ['name', 'describe', 'targetTime', 'reaction', 'contacts', 'status', 'delete'];
  dataSource = new MatTableDataSource<Element>(this.ELEMENT_DATA);
  minDate = new Date();
  startDate = new FormControl(new Date(+this.minDate + 86400000));
  selection = new SelectionModel<Element>(true, []);

  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(public dialog: MatDialog, private snackBar: MatSnackBar, private http: HttpClient) { }

  onDateChange(event: MatDatepickerInputEvent<Date>) {
    this.ELEMENT_DATA = [];
    this.http.get('http://localhost:8080/tasks').subscribe(data => {
      Object.values(data).forEach(item => {
        this.ELEMENT_DATA.push({
          id: item.id,
          name: item.name,
          describe: item.describe,
          targetTime: item.targetTime,
          reaction: item.reaction.type,
          contacts: item.contacts,
          status: item.status
        });
      });
      this.dataSource = new MatTableDataSource<Element>(this.ELEMENT_DATA);
      this.ngAfterViewInit();
    });
  }

  deleteRow(row) {
    var index = this.dataSource.data.indexOf(row);
    this.dataSource.data.splice(index, 1);
    this.dataSource._updateChangeSubscription();
    this.http.delete('http://localhost:8080/tasks/' + row.id).subscribe(data =>{

    });
    this.openSnackBar('Id of deleted task : ' + row.id, 'OK');
  }

  deleteAll() {
    this.dataSource.data = [];
    this.dataSource._updateChangeSubscription();
    this.http.get('http://localhost:8080/tasks/*').subscribe(data => {

    });
    this.openSnackBar('All tasks deleted.', 'OK');
  }

  openSnackBar(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 2000,
    });
  }

  openDialog(): void {
    let dialogRef = this.dialog.open(DialogOverviewExampleDialog, {

    });
    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }


  ngOnInit() {
  }

}

export interface Element {
  id: UUID;
  name: string;
  describe: string;
  targetTime: Date;
  contacts: string;
  reaction: Reaction;
  status: string;
}

export interface Reaction {
  type: string;
  value: string;
}



@Component({
  selector: 'dialog-overview',
  templateUrl: './dialog-overview.html',
  styleUrls: ['./dialog-overview.css']
})
export class DialogOverviewExampleDialog {


  uuid: UUID = UUID.UUID();
  num: number;

  types = [
    { value: 'SENDER', viewValue: 'MailSender' },
    { value: 'OUTPUT', viewValue: 'Output' },
    { value: 'SLEEP', viewValue: 'Sleep' }
  ];


  constructor(private http: HttpClient,
    public dialogRef: MatDialogRef<DialogOverviewExampleDialog>,
    @Inject(MAT_DIALOG_DATA) public data: any) {
  }

  postData(name : string , describe : string , date : string , time : string , type : string , value : string , contacts : string) {
    let uuid = UUID.UUID();
    return this.http.post(
      'http://localhost:8080/tasks',
      JSON.stringify({
        id: uuid,
        name: name,
        describe: describe,
        targetTime: +new Date(date + ' ' + time),
        reaction: {
          type: type,
          value: value
        },
        contacts: contacts.split(','),
        status: 'SCHEDULED'
      })
      , {
        headers: new HttpHeaders({
          'Content-Type': 'application/json',
          'Accept': 'application/json',
          'Access-Control-Allow-Headers': 'Content-Type',
          'Access-Control-Allow-Origin': '*'
        }),
        observe: 'response'
      }).subscribe(data => alert(JSON.stringify(data)));
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

}
