  import { Component, OnInit, ViewChild, Inject , Output ,  Input } from '@angular/core';
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
  import {PaginatorComponent} from 'app/paginator/paginator.component'
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

    data: Object;
    event: string;
    count = 0;
    displayedColumns = ['name', 'describe', 'targetTime', 'reaction', 'contacts', 'status', 'delete'];
    dataSource = new MatTableDataSource<Element>(this.ELEMENT_DATA);
    minDate = new Date();
    startDate = new FormControl(new Date(+this.minDate + 86400000));
    selection = new SelectionModel<Element>(true, []);
    size : number = 0;
    page : number = 1;
    lastRequest: string;
    name: string;
    @ViewChild(MatPaginator) paginator: MatPaginator;

    constructor( private http: HttpClient , public dialog: MatDialog, private httpService: HttpServiceComponent, private snackBar: MatSnackBar) { }

    onDateChange(event: MatDatepickerInputEvent<Date>) {
      //this.httpService.getTasks(this.page);
      //this.data = this.httpService.data;
      this.ELEMENT_DATA = [];
      this.http.get('http://localhost:8080/tasks?next='+this.page).subscribe(data =>{
      Object.values(data).forEach(item => {
        this.ELEMENT_DATA.push({
          id: item.id,
          name: item.name,
          describe: item.describe,
          targetTime: item.targetTime,
          reaction: JSON.parse(JSON.stringify(item.reaction)).type,
          contacts: item.contacts,
          status: item.status,
          size: item.size
        });
      });
      this.size = parseInt(this.ELEMENT_DATA[0].size/20) +1;
      this.dataSource = new MatTableDataSource<Element>(this.ELEMENT_DATA);
      this.ngAfterViewInit();
      this.lastRequest = 'Time';
    });
    }
    startSearch(): void {
      let dialogRef = this.dialog.open(DialogSearchExampleDialog, {
        height: '150px',
    width: '350px',
      });
      dialogRef.componentInstance.page = this.page;
      dialogRef.beforeClose().subscribe(result => {
          //this.data = dialogRef.componentInstance.tasks;
          this.ELEMENT_DATA = [];
          this.name = dialogRef.componentInstance.name;
          if(this.name != ""){
          this.http.get('http://localhost:8080/tasks?name='+this.name+'&next='+this.page).subscribe(data =>{
          Object.values(data).forEach(item => {
            this.ELEMENT_DATA.push({
              id: item.id,
              name: item.name,
              describe: item.describe,
              targetTime: item.targetTime,
              reaction: JSON.parse(JSON.stringify(item.reaction)).type,
              contacts: item.contacts,
              status: item.status,
              size: item.size
            });
          });

          this.size = parseInt(this.ELEMENT_DATA[0].size/20) +1;
          this.dataSource = new MatTableDataSource<Element>(this.ELEMENT_DATA);
          this.ngAfterViewInit();
          this.lastRequest =   dialogRef.componentInstance.lastRequest;
          console.log('The dialog was closed');
        });
      }
      else{
        this.http.get('http://localhost:8080/tasks?next='+this.page).subscribe(data =>{
        Object.values(data).forEach(item => {
          this.ELEMENT_DATA.push({
            id: item.id,
            name: item.name,
            describe: item.describe,
            targetTime: item.targetTime,
            reaction: JSON.parse(JSON.stringify(item.reaction)).type,
            contacts: item.contacts,
            status: item.status,
            size: item.size
          });
        });

        this.size = parseInt(this.ELEMENT_DATA[0].size/20) +1;
        this.dataSource = new MatTableDataSource<Element>(this.ELEMENT_DATA);
        this.ngAfterViewInit();
        this.lastRequest =   dialogRef.componentInstance.lastRequest;
        console.log('The dialog was closed');
      });
    }
  });
}

    onPaginatorChange(): void{
      if(this.lastRequest == 'Time'){

        this.ELEMENT_DATA = [];

        this.http.get('http://localhost:8080/tasks?next='+this.page).subscribe(data =>{
        Object.values(data).forEach(item => {
          this.ELEMENT_DATA.push({
            id: item.id,
            name: item.name,
            describe: item.describe,
            targetTime: item.targetTime,
            reaction: JSON.parse(JSON.stringify(item.reaction)).type,
            contacts: item.contacts,
            status: item.status,
            size: item.size
          });
        });
        this.size = parseInt(this.ELEMENT_DATA[0].size/20) +1;
        this.dataSource = new MatTableDataSource<Element>(this.ELEMENT_DATA);
        this.ngAfterViewInit();
        this.lastRequest = 'Time';
      });

        }
        else if(this.lastRequest == 'All'){
          this.ELEMENT_DATA = [];
          this.http.get('http://localhost:8080/tasks?next='+this.page).subscribe(data =>{
          Object.values(data).forEach(item => {
            this.ELEMENT_DATA.push({
              id: item.id,
              name: item.name,
              describe: item.describe,
              targetTime: item.targetTime,
              reaction: JSON.parse(JSON.stringify(item.reaction)).type,
              contacts: item.contacts,
              status: item.status,
              size: item.size
            });
          });
          this.size = parseInt(this.ELEMENT_DATA[0].size/20) +1;
          this.dataSource = new MatTableDataSource<Element>(this.ELEMENT_DATA);
          this.ngAfterViewInit();
          this.lastRequest = 'All';
        });

        }
      else if(this.lastRequest == 'Name'){

          this.ELEMENT_DATA = [];
          this.http.get('http://localhost:8080/tasks?name='+this.name+'&next='+this.page).subscribe(data =>{
          Object.values(data).forEach(item => {
            this.ELEMENT_DATA.push({
              id: item.id,
              name: item.name,
              describe: item.describe,
              targetTime: item.targetTime,
              reaction: JSON.parse(JSON.stringify(item.reaction)).type,
              contacts: item.contacts,
              status: item.status,
              size: item.size
            });
          });
          this.size = parseInt(this.ELEMENT_DATA[0].size/20) +1;
          this.dataSource = new MatTableDataSource<Element>(this.ELEMENT_DATA);
          this.ngAfterViewInit();
          this.lastRequest =  'Name';
        });

        }
      else{alert('Something`s happening wrong')}


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
    size: string;
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
  @Component({
    selector: 'dialog-search',
    templateUrl: './dialog-search.html',
    styleUrls: ['./dialog-search.css'],
  })

  export class DialogSearchExampleDialog {
      /*tasks : Object;
      page : number = 1;*/
      lastRequest: string;
      name: string;
    constructor(
   private httpService: HttpServiceComponent, public dialogRef: MatDialogRef<DialogOverviewExampleDialog>,
      @Inject(MAT_DIALOG_DATA) public data: any  ) {
     }

     onSearchClick(taskName) {
       //tasks = this.httpService.getDatabyName(taskName);
       /*this.tasks = [ {name:'string',
        describe:'stringasdasd',
        targetTime:'string',
        completedTime: 'string',
        reaction:{value:'string',type:'string'},
        contacts:'string',
        status:'string'}];*/
      /*if(taskName == ""){
        this.httpService.getTasks(this.page);
        this.tasks = this.httpService.data;
        this.lastRequest = 'All';
      }
      else{
        this.httpService.getTasksByName(taskName , this.page);
        this.tasks = this.httpService.data;
        this.lastRequest = 'Name';
      }*/
      this.name = taskName;
      if(taskName != "")
        this.lastRequest = 'Name';
      else
        this.lastRequest = 'All';
      this.dialogRef.close();

     }
    onNoClick(): void {
      this.dialogRef.close();
    }
  }
