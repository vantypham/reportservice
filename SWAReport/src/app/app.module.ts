import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {NgxMaterialTimepickerModule} from 'ngx-material-timepicker';
import { DatePipe } from '@angular/common';
import * as CanvasJSAngularChart from '../assets/canvasjs.angular.component';
var CanvasJSChart = CanvasJSAngularChart.CanvasJSChart;

import { AppComponent } from './app.component';
import { TopBarComponent } from './top-bar/top-bar.component';
import { HttpErrorHandler } from './http-error-handler.service';
import { MessageService } from './message.service';
import { MessagesComponent } from './messages/messages.component';
import { ChartComponent } from './components/line-chart/chart.component';
import {ReportFilter} from './components/filter-section/reportFilter.component';
import { DatepickerComponent } from './components/material/datepicker/datepicker.component';
import { MaterialExampleModule } from './components/material/datepicker/material.module';
import { FilterNChartComponent } from './components/filter-n-chart/filter-n-chart.component';
import { DropdownListComponent } from './components/material/dropdown-list/dropdown-list.component';

import { HighchartsChartModule } from 'highcharts-angular';


@NgModule({
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    HttpClientModule,
    // HttpClientXsrfModule.withOptions({
    //   cookieName: 'My-Xsrf-Cookie',
    //   headerName: 'My-Xsrf-Header',
    // }),
    // RouterModule.forRoot([
    //   { path: '', component: ChartComponent }
    // ]),
    MaterialExampleModule,
    BrowserAnimationsModule,
    NgxMaterialTimepickerModule,
    HighchartsChartModule
  ],
  declarations: [
    AppComponent,
    TopBarComponent,
    CanvasJSChart,
    ChartComponent,
    ReportFilter,
    DatepickerComponent,
    MessagesComponent,
    FilterNChartComponent,
    DropdownListComponent
  ],
  providers: [
    HttpErrorHandler,
    DatePipe,
    MessageService
  ],
  bootstrap: [
    AppComponent
  ]
})
export class AppModule { }