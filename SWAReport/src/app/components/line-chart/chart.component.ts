import {
    Component,
    Input,
    OnChanges,
    OnInit,
    SimpleChanges,
} from "@angular/core";

import * as CanvasJSAngularChart from "../../../assets/canvasjs.angular.component";
var CanvasJS = CanvasJSAngularChart.CanvasJS;

import { ChartDTO, ChartModel, Report } from "../../services/report";
import { ReportService } from "../../services/report.service";
import { TopicFilter } from "../../model/topicFilter";

import * as Highcharts from "highcharts";
import { DatePipe } from "@angular/common";

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
            accessibility: {
                rangeDescription: "Time",
            },
        },
        series: []
    };

    constructor(
        private reportService: ReportService,
        private datePipe: DatePipe
    ) {}

    ngOnChanges(changes: SimpleChanges): void {
        if (this.topicFilter.actionType == "GenerateReport") {
            this.getReportByTimeRange(
                this.topicFilter.topicName,
                this.topicFilter.startDateTime,
                this.topicFilter.endDateTime
            );
        } else if (this.topicFilter.actionType == "DownloadCsv") {
            this.downloadCsv(
                this.topicFilter.topicName,
                this.topicFilter.startDateTime,
                this.topicFilter.endDateTime
            );
        }
    }

    ngOnInit() {
        // this.chart = new CanvasJS.Chart("chartContainer", {
        //     theme: "light1", // "light2", "dark1", "dark2"
        //     title: {
        //         text: "Reporting Service",
        //     },
        //     data: [
        //         {
        //             type: "line", // Change type to "bar", "area", "spline", "pie",etc.
        //             dataPoints: [],
        //         },
        //     ],
        // });
        // this.chart.render();
        // if (this.topicFilter.actionType == "GenerateReport") {
        //     this.getReportByTimeRange(
        //         this.topicFilter.topicName,
        //         this.topicFilter.startDateTime,
        //         this.topicFilter.endDateTime
        //     );
        // }
    }

    downloadCsv(
        topicName: string,
        startDatetime: number,
        endDateTime: number
    ): void {
        this.reportService.generateCsv(topicName, startDatetime, endDateTime);
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

    updateChart(reports: Report[]): void {
        // this.chartModel = this.reportService.convertToChartModel(reports);
        // this.chart.options.data = this.chartModel;
        // this.chart.render();

        this.chartOptions.series = this.reportService.convertToHighChartModel(reports);
        this.chartRef = Highcharts.chart('highchartContainer', this.chartOptions);
    }
}
