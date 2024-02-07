import {Component, OnInit, AfterContentInit, ChangeDetectorRef} from '@angular/core';
import {ReportService} from "../services/report.service";
import {DatePipe} from "@angular/common";
import {LoadingService} from "../services/loading.service";

@Component({
  selector: 'app-top-bar',
  templateUrl: './top-bar.component.html',
  styleUrls: ['./top-bar.component.css']
})
export class TopBarComponent implements OnInit {

  isLoading: boolean = false;

  constructor(
      private loadingService: LoadingService,
      private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadingService.isLoading$.subscribe(isLoading => {
      this.isLoading = isLoading;
      this.cdr.detectChanges();
    });
  }
}
