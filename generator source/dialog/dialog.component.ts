import { Component, OnInit , Inject} from '@angular/core';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';
import { UUID } from 'angular2-uuid';

@Component({
  selector: 'app-dialog',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.css']
})
export class DialogComponent implements OnInit {

  ngOnInit() {

  }

  constructor(public dialog: MatDialog) {}

  openDialog(): void {
    let dialogRef = this.dialog.open(DialogOverviewExampleDialog, {
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }


}

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
    let uuid = UUID.UUID();
   }

  onNoClick(): void {
    this.dialogRef.close();
  }

}
