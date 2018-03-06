import { Component, OnInit, ViewChild, Inject, Output, Input } from '@angular/core';
import { MatPaginator, MatTableDataSource } from '@angular/material';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA, MatCheckboxModule } from '@angular/material';
import { MatDatepickerInputEvent } from '@angular/material/datepicker';
import { UUID } from 'angular2-uuid';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { FormControl } from '@angular/forms';
import { MomentDateAdapter } from '@angular/material-moment-adapter';
import { DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE } from '@angular/material/core';
import { SelectionModel } from '@angular/cdk/collections';
import { MatSnackBar } from '@angular/material/snack-bar';
import { PaginatorComponent } from 'app/paginator/paginator.component'
import { OwlDateTimeModule, OwlNativeDateTimeModule } from 'ng-pick-datetime';
import {MatDividerModule} from '@angular/material/divider';
import {MatListModule} from '@angular/material/list';
import {MatSidenav} from '@angular/material/sidenav';
import {MatToolbarModule} from '@angular/material';
import {Router, ActivatedRoute, Params } from "@angular/router";


@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

  const task_manager_port = 44444;
  const user_manager_port = 55555;


  name : string;
  ELEMENT_DATA: Element[] = [

  ];

  displayedColumns = ['name', 'email', 'description' , 'delete'];
  dataSource = new MatTableDataSource<User>(this.ELEMENT_DATA);


  constructor(public dialog: MatDialog , public http : HttpClient , private snackBar: MatSnackBar) { }

  ngOnInit() {
    this.getUsers();
  }

  getUsers(){
    this.http.get('http://localhost:55555/users').subscribe(data => {
      this.parse(data);
    });

  }

  parse(data) {
    //var data_size = data[0];
    //data = Object.values(data[1]);
    //data = JSON.stringify(data);
    this.ELEMENT_DATA = []
    Object.values(data).forEach(item => {
      this.ELEMENT_DATA.push({
        name: item.name,
        email: item.email,
        description: item.description,//moment(item.date).format('hh:mm DD.MM.YYYY'),
      });
    });
    /*if (data_size % this.size == 0) {
      this.pages = parseInt(data_size / this.size);
    }
    else {
      this.pages = parseInt(data_size / this.size) + 1;
    }*/
    this.dataSource = new MatTableDataSource<User>(this.ELEMENT_DATA);
  }

  findByName(name : string){
    if(name == ""){
      this.getUsers();
    }
    else{
      this.http.get('http://localhost:55555/users?name=' + name).subscribe(data => {
        this.parse(data);
      });
    }

  }

  /*findByEmail(email : string){
    //if(email != ""){
      this.http.get('http://localhost:55555/users?email=' + email).subscribe(data => {
        this.ELEMENT_DATA = []
        Object.values(data).forEach(item => {
          this.ELEMENT_DATA.push({
            name: item.name,
            email: item.email,
            description: item.description,
            notification_time: item.notification_time//moment(item.date).format('hh:mm DD.MM.YYYY'),
          });
        });
        /*if (data_size % this.size == 0) {
          this.pages = parseInt(data_size / this.size);
        }
        else {
          this.pages = parseInt(data_size / this.size) + 1;
        }*/
        //this.dataSource = new MatTableDataSource<User>(this.ELEMENT_DATA);
      //});
  //  }
    //else{
      //this.getUsers();
  //  }
//}*/


  deleteRow(row) {
    var index = this.dataSource.data.indexOf(row);
    this.dataSource.data.splice(index, 1);
    this.dataSource._updateChangeSubscription();
    this.http.delete('http://localhost:55555/users/?email=' + row.email).subscribe(data => {

    });
    this.openSnackBar('Email of deleted task : ' + row.email, 'OK');
  }

  /*deleteAll() {
    this.dataSource.data = [];
    this.dataSource._updateChangeSubscription();
    this.http.get('http://localhost:8080/tasks/*').subscribe(data => {

    });
    this.openSnackBar('All tasks deleted.', 'OK');
  }*/

  openSnackBar(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 2000,
    });
  }


  openDialog(): void {
    let dialogRef = this.dialog.open(DialogOverviewDialog, {
    });
    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

}

export interface User{
  name : string;
  email : string;
  description : string;
}

@Component({
  selector: 'dialog-overview',
  templateUrl: './dialog-overview.html',
  styleUrls: ['./dialog-overview.css']
})
export class DialogOverviewDialog {

  const user_manager_port = 55555;

  constructor(private http: HttpClient,
    public dialogRef: MatDialogRef<DialogOverviewExampleDialog>,
    @Inject(MAT_DIALOG_DATA) public data: any) {
  }

  addUser(name: string, email : string , description: string) {
    return this.http.post(
      'http://localhost:55555/users',
      JSON.stringify({
        name: name,
        email: email,
        description: description
      })
      , {
        headers: new HttpHeaders({
          'Content-Type': 'application/json',
          'Accept': 'application/json',
          'Access-Control-Allow-Headers': 'Content-Type',
          'Access-Control-Allow-Origin': '*'
        }),
        observe: 'response'
      }).subscribe(data =>{
        this.refresh();
      });
  }

  refresh(): void {
    window.location.reload();
}

  onNoClick(): void {
    this.dialogRef.close();
  }

}
