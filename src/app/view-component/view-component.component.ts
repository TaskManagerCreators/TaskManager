import { Component, OnInit, ViewChild, Inject } from '@angular/core';
import { MatPaginator, MatTableDataSource } from '@angular/material';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA, MatCheckboxModule } from '@angular/material';
import { HttpServiceComponent } from 'app/http-service/http-service.component';
import { MatDatepickerInputEvent } from '@angular/material/datepicker';
import { UUID } from 'angular2-uuid';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { FormControl } from '@angular/forms';
import { MomentDateAdapter } from '@angular/material-moment-adapter';
import { DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE } from '@angular/material/core';
import { SelectionModel } from '@angular/cdk/collections';
import { MatSnackBar } from '@angular/material/snack-bar';


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

  data: Object;
  event: string;
  count = 0;
  displayedColumns = ['name', 'describe', 'targetTime', 'reaction', 'contacts', 'status', 'delete'];
  dataSource = new MatTableDataSource<Element>(this.ELEMENT_DATA);
  minDate = new Date();
  startDate = new FormControl(new Date(+this.minDate + 86400000));
  selection = new SelectionModel<Element>(true, []);

  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(public dialog: MatDialog, private httpService: HttpServiceComponent, private snackBar: MatSnackBar) { }

  onDateChange(event: MatDatepickerInputEvent<Date>) {
    this.httpService.getTasks();
    this.data = this.httpService.data;
    this.ELEMENT_DATA = [];
    alert(this.data);
    JSON.parse(JSON.stringify(this.data)).forEach(item => {
      this.ELEMENT_DATA.push({
        id: item.id,
        name: item.name,
        describe: item.describe,
        targetTime: item.targetTime,
        reaction: JSON.parse(JSON.stringify(item.reaction)).type,
        contacts: item.contacts,
        status: item.status
      });
    });
    this.dataSource = new MatTableDataSource<Element>(this.ELEMENT_DATA);
    this.ngAfterViewInit();
  }

  deleteRow(row) {
    var index = this.dataSource.data.indexOf(row);
    this.dataSource.data.splice(index, 1);
    this.dataSource._updateChangeSubscription();
    // REVIEW: request
    this.openSnackBar('Id of deleted task : ' + row.id, 'OK');
  }

  deleteAll() {
    this.dataSource.data = [];
    this.dataSource._updateChangeSubscription();
    // REVIEW: request
    this.openSnackBar('All tasks deleted.', 'OK');
  }

  openSnackBar(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 2000,
    });
  }

  openDialog(): void {
    let dialogRef = this.dialog.open(DialogOverviewExampleDialog, {
      height: '400px',
      width: '600px',
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
    { value: 'sender-0', viewValue: 'MailSender' },
    { value: 'output-1', viewValue: 'Output' },
    { value: 'sleep-2', viewValue: 'Sleep' }
  ];


  constructor(
    public dialogRef: MatDialogRef<DialogOverviewExampleDialog>,
    @Inject(MAT_DIALOG_DATA) public data: any) {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

}
