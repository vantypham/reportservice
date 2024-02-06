import { Component, Output, EventEmitter, OnInit } from '@angular/core';
import { ReportService } from '../../../services/report.service';

@Component({
  selector: 'dropdown-list',
  templateUrl: './dropdown-list.component.html',
  styleUrls: ['./dropdown-list.component.css'],
  providers: [ReportService]
})
export class DropdownListComponent implements OnInit {
  topics: string[] = ["All"];
  chooseTopic: string = "";

  chooseTopics: string[] = [];

  @Output()
  topicChange: EventEmitter<string> = new EventEmitter<string>();

  @Output()
  topicsChange: EventEmitter<string[]> = new EventEmitter<string[]>();

  ngOnInit(): void {
    this.getTopics();
    this.chooseTopic = this.topics[0];
    this.chooseTopics.push(this.topics[0]);
  }

  constructor(private reportService: ReportService) {
  }

  getTopics(): void {
    this.reportService.getTopics().subscribe(data => {
      this.topics.push(...data);
    });
  }

  getTopicChange(event: any) {
    console.log(event);
    if(event.value == this.topics[0]){
      this.topicChange.emit("");
    } else {
      this.topicChange.emit(event.value);
    }
  }

  onTopicsChange(event: any) {
    console.log(event);
    if(event.value[0] == this.topics[0]){
      this.topicsChange.emit([]);
    } else {
      this.topicsChange.emit(event.value);
    }
  }

}
