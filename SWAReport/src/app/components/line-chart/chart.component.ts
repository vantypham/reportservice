import {
    Component,
    Input,
    OnChanges,
    OnInit,
    SimpleChanges,
} from "@angular/core";

import { ChartDTO, ChartModel, Report } from "../../services/report";
import { ReportService } from "../../services/report.service";
import { TopicFilter } from "../../model/topicFilter";

import * as Highcharts from "highcharts";
import { DatePipe } from "@angular/common";

import noData from 'highcharts/modules/no-data-to-display';
noData(Highcharts);

import * as moment from 'moment-timezone';

@Component({
    selector: "app-chart",
    templateUrl: "chart.component.html",
    providers: [ReportService],
})
export class ChartComponent implements OnInit, OnChanges {
    chart: any;
    chartModel: ChartModel[] = [];
    topics: string[] = [];

    @Input()
    topicFilter: TopicFilter = {} as TopicFilter;

    Highcharts: typeof Highcharts = Highcharts;
    chartRef: Highcharts.Chart = {} as Highcharts.Chart;
    chartOptions: Highcharts.Options = {
        lang: {
            noData: 'No data to display',
        },
        title: {
            text: "",
        },
        colors: ['#058DC7', '#50B432', '#ED561B', '#DDDF00', '#24CBE5', '#64E572', '#FF9655', '#FFF263', '#6AF9C4'],
        yAxis: {
            title: {
                text: "Normalized Sensitivity Index",
                align: "low",
            },
        },
        xAxis: {
            type: 'datetime',
            labels: {
                formatter: function () {
                    // Format timestamp to display in US Central Time (GMT-6)
                    return moment(this.value).tz('America/Chicago').format('HH:mm');
                }
            },
            accessibility: {
                rangeDescription: "Time",
            },
        },
        series: []
    };

    isLoading: boolean = true;

    constructor(
        private reportService: ReportService,
        private datePipe: DatePipe
    ) {}

    ngOnInit() {
        // if (this.topicFilter.actionType == "GenerateReport") {
        //     this.getReportByTimeRange(
        //         this.topicFilter.topicName,
        //         this.topicFilter.startDateTime,
        //         this.topicFilter.endDateTime
        //     );
        // }
        this.chartRef = Highcharts.chart('highchartContainer', this.chartOptions);
    }

    ngOnChanges(changes: SimpleChanges): void {
        if (this.topicFilter.actionType == "GenerateReport") {
            // this.getReportByTimeRange(
            //     this.topicFilter.topicName,
            //     this.topicFilter.startDateTime,
            //     this.topicFilter.endDateTime
            // );
            if (this.topicFilter.topicNames.length > 0) {
                this.getReportByMultiTopicAndTimeRange(
                    this.topicFilter.topicNames,
                    this.topicFilter.startDateTime,
                    this.topicFilter.endDateTime
                );
            } else {
                this.getReportByTimeRange(
                    "",
                    this.topicFilter.startDateTime,
                    this.topicFilter.endDateTime
                );
            }

        } else if (this.topicFilter.actionType == "DownloadCsv") {
            // this.downloadCsv(
            //     this.topicFilter.topicName,
            //     this.topicFilter.startDateTime,
            //     this.topicFilter.endDateTime
            // );
            if (this.topicFilter.topicNames.length > 0) {
                this.downloadCsvMultiTopics(
                    this.topicFilter.topicNames,
                    this.topicFilter.startDateTime,
                    this.topicFilter.endDateTime
                );
            } else {
                this.downloadCsv(
                    "",
                    this.topicFilter.startDateTime,
                    this.topicFilter.endDateTime
                );
            }
        }
    }

    downloadCsv(
        topicName: string,
        startDatetime: number,
        endDateTime: number
    ): void {
        this.reportService.generateCsv(topicName, startDatetime, endDateTime);
    }

    downloadCsvMultiTopics(
        topicNames: string[],
        startDatetime: number,
        endDateTime: number
    ): void {
        this.reportService.generateCsvMultiTopics(topicNames, startDatetime, endDateTime);
    }

    getReportByTimeRange(
        topicName: string,
        startDatetime: number,
        endDateTime: number
    ): void {
        this.reportService
            .getReportByTimeRange(topicName, startDatetime, endDateTime)
            .subscribe((data) => {
                if (data.length > 0) {
                    this.updateChart(data);
                }
            });
    }

    getReportByMultiTopicAndTimeRange(
        topicNames: string[],
        startDatetime: number,
        endDateTime: number
    ): void {
        this.reportService
            .getReportByMultiTopicAndTimeRange(topicNames, startDatetime, endDateTime)
            .subscribe((data) => {
                if (data.length > 0) {
                    this.updateChart(data);
                }
            });
    }

    updateChart(reports: Report[]): void {
        this.chartOptions.series = this.reportService.convertToHighChartModel(reports);
        this.chartRef = Highcharts.chart('highchartContainer', this.chartOptions);
    }
}
