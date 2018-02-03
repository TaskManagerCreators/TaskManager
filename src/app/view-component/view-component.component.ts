import { Component, OnInit , ViewChild , Inject } from '@angular/core';
import {MatPaginator , MatTableDataSource} from '@angular/material';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';
import { UUID } from 'angular2-uuid';

@Component({
  selector: 'app-view-component',
  templateUrl: './view-component.component.html',
  styleUrls: ['./view-component.component.css']
})
export class ViewComponentComponent implements OnInit {

  displayedColumns = ['name'];
  dataSource = new MatTableDataSource<Element>(ELEMENT_DATA);
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

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }



  ngOnInit() {
  }

}

export interface Element {
  name: string;
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
