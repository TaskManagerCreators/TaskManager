import {FormControl} from '@angular/forms';
import {AppComponent} from './app.component';
import {platformBrowserDynamic} from '@angular/platform-browser-dynamic';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MomentDateAdapter} from '@angular/material-moment-adapter';
import {DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE} from '@angular/material/core';
import {SelectionModel} from '@angular/cdk/collections';
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
import { HttpServiceComponent} from './http-service/http-service.component';
import { ViewComponentComponent , DialogOverviewExampleDialog } from './view-component/view-component.component';

@NgModule({
  declarations: [
    AppComponent,
    HttpServiceComponent,
    ViewComponentComponent,
    DialogOverviewExampleDialog,
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
    MatCheckboxModule,
    MatSnackBarModule,
  ],
  entryComponents: [ViewComponentComponent ,  DialogOverviewExampleDialog],
  providers: [HttpServiceComponent],
  bootstrap: [AppComponent ],
})
export class AppModule { }

platformBrowserDynamic().bootstrapModule(AppModule);
