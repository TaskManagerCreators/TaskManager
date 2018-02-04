import { Component, OnInit , ViewChild , Inject , Output , Injectable} from '@angular/core';
import {MatPaginator , MatTableDataSource} from '@angular/material';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';
import { UUID } from 'angular2-uuid';
import { HttpService } from '../http.service';
@Component({
  selector: 'app-view-component',
  templateUrl: './view-component.component.html',
  styleUrls: ['./view-component.component.css'],
  providers: [HttpService]
})
export class ViewComponentComponent implements OnInit {

  displayedColumns = ['name' , 'description' , 'targetDate' ];
  dataSource : MatTableDataSource<Task>(tasks);
  minDate = new Date();

  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(public dialog: MatDialog) {}

  openDialog(): void {
    let dialogRef = this.dialog.open(DialogOverviewExampleDialog, {
      height: '400px',
  width: '600px',
    });
    dialogRef.afterClosed().subscribe(result => {
        console.log('The dialog was closed');
      });
  }

  startSearch(): void {
    let dialogRef = this.dialog.open(DialogSearchExampleDialog, {
      height: '150px',
  width: '350px',
    });
    dialogRef.beforeClose().subscribe(result => {
        tasks = dialogRef.componentInstance.tasks;
        this.dataSource = new MatTableDataSource<Task>(tasks);
        console.log('The dialog was closed');
      });
  }



  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }


  ngOnInit() {
      this.dataSource = new MatTableDataSource<Task>(tasks);
  }

}

export interface Element {
  name: string;
}
export interface Task{
  id: UUID;
  name:string,
  describe:string,
  targetTime:string,
  completedTime: string,
  reaction:{value:string,type:string},
  contacts:string,
  status:string
}

const ELEMENT_DATA: Element[] = [
  {name: 'Hydrogen'},
  {name: 'Hydrogen'},
  {name: 'Hydrogen'},
  {name: 'Hydrogen'},
  {name: 'Hydrogen'},
  {name: 'Hydrogen'},
  {name: 'Hydrogen'},
  {name: 'Hydrogen'},
  {name: 'Hydrogen'},
  {name: 'Hydrogen'},
  {name: 'Hydrogen'},
  {name: 'Hydrogen'},
  {name: 'Hydrogen'},
  {name: 'Hydrogen'},
];
 let tasks : Task[] = [
   {
     id:'asd',
     name:'string',
   describe:'string',
   targetTime:'string',
   completedTime: 'string',
   reaction:{value:'string',type:'string'},
   contacts:'string',
   status:'string'}
];

@Component({
  selector: 'dialog-overview',
  templateUrl: './dialog-overview.html',
  styleUrls: ['./dialog-overview.css']
})
export class DialogOverviewExampleDialog {


uuid : UUID = UUID.UUID();
num : number;

  types = [
    {value: 'sender-0', viewValue: 'MailSender'},
    {value: 'output-1', viewValue: 'Output'},
    {value: 'sleep-2', viewValue: 'Sleep'}
  ];


  constructor(
    public dialogRef: MatDialogRef<DialogOverviewExampleDialog>,
    @Inject(MAT_DIALOG_DATA) public data: any  ) {
   }

  onNoClick(): void {
    this.dialogRef.close();
  }

}
@Component({
  selector: 'dialog-search',
  templateUrl: './dialog-search.html',
  styleUrls: ['./dialog-search.css'],
  providers: [HttpService]
})
@Injectable()
export class DialogSearchExampleDialog {
  @Output() tasks : Task[];

  constructor(
    private httpService: HttpService , public dialogRef: MatDialogRef<DialogOverviewExampleDialog>,
    @Inject(MAT_DIALOG_DATA) public data: any  ) {
   }

   onSearchClick(taskName : string) {
     //tasks = this.httpService.getDatabyName(taskName);
     /*this.tasks = [ {name:'string',
      describe:'stringasdasd',
      targetTime:'string',
      completedTime: 'string',
      reaction:{value:'string',type:'string'},
      contacts:'string',
      status:'string'}];*/
    this.httpService.getData().subscribe(data => this.tasks = data);
    this.dialogRef.close();
   }
  onNoClick(): void {
    this.dialogRef.close();
  }
}
