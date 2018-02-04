import {FormControl} from '@angular/forms';
import {AppComponent} from './app.component';
import {platformBrowserDynamic} from '@angular/platform-browser-dynamic';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

import {
  MatFormFieldModule,
  MatAutocompleteModule,
  MatButtonModule,
  MatButtonToggleModule,
  MatCardModule,
  MatCheckboxModule,
  MatChipsModule,
  MatDatepickerModule,
  MatDialogModule,
  MatExpansionModule,
  MatGridListModule,
  MatIconModule,
  MatInputModule,
  MatListModule,
  MatMenuModule,
  MatNativeDateModule,
  MatPaginatorModule,
  MatProgressBarModule,
  MatProgressSpinnerModule,
  MatRadioModule,
  MatRippleModule,
  MatSelectModule,
  MatSidenavModule,
  MatSliderModule,
  MatSlideToggleModule,
  MatSnackBarModule,
  MatSortModule,
  MatTableModule,
  MatTabsModule,
  MatToolbarModule,
  MatTooltipModule,
  MatStepperModule,
} from '@angular/material';

import {HttpModule} from '@angular/http';
import {CdkTableModule} from '@angular/cdk/table';
import 'hammerjs';

import { HttpClientModule }   from '@angular/common/http';
import { ViewComponentComponent , DialogOverviewExampleDialog , DialogSearchExampleDialog} from './view-component/view-component.component';
import { HttpserviceComponent } from './httpservice/httpservice.component';

@NgModule({
  declarations: [
    AppComponent,

    ViewComponentComponent,
    DialogOverviewExampleDialog,
    DialogSearchExampleDialog,
    HttpserviceComponent,
  ],
  imports: [
    MatSelectModule,
    MatRadioModule,
    MatCardModule,
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpModule ,
    MatButtonModule,
    MatButtonToggleModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatInputModule,
    ReactiveFormsModule,
    MatDialogModule,
    HttpClientModule,
    MatPaginatorModule,
    MatTableModule,
    MatToolbarModule,
    MatIconModule,


  ],
  entryComponents: [ViewComponentComponent ,  DialogOverviewExampleDialog , DialogSearchExampleDialog],
  providers: [],
  bootstrap: [AppComponent ],
})
export class AppModule { }

platformBrowserDynamic().bootstrapModule(AppModule);
