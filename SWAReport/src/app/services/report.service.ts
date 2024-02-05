import { Injectable } from "@angular/core";
import { HttpClient, HttpParams } from "@angular/common/http";
import { HttpHeaders } from "@angular/common/http";
import { DatePipe } from "@angular/common";

import {map, Observable, of} from "rxjs";
import { catchError } from "rxjs/operators";

import { Report, ReportObj, ChartModel, ChartDTO } from "./report";
import { HttpErrorHandler, HandleError } from "../http-error-handler.service";
import { environment } from "../../environments/environment";

const httpOptions = {
    headers: new HttpHeaders({
        "Content-Type": "application/json",
    }),
};
const baseUrl = environment.baseUrl;

@Injectable()
export class ReportService {
    byTimeRangeUrl = baseUrl;
    byTopicUrl = baseUrl + "/topic/";
    topicsUrl = baseUrl + "/topics";
    csvReportUrl = baseUrl + "/csv";

    private handleError: HandleError;

    constructor(
        private http: HttpClient,
        private datePipe: DatePipe,
        httpErrorHandler: HttpErrorHandler
    ) {
        this.handleError = httpErrorHandler.createHandleError("ReportService");
    }

    getReportByTimeRange(
        topicName: string,
        from: number,
        to: number
    ): Observable<any> {
        let params = new HttpParams();

        // TODO uncomment this once real API is available
        params = params.set('from', from);
        params = params.set('to', to);
        params = params.set('topicName', topicName);

        const options = from && to ? { params: params } : {};

        return this.http
            .get<any>(this.byTimeRangeUrl, options)
            .pipe(
                map((response) => {
                    // return response.data;
                    return response;
                }),
                catchError(this.handleError("getReportByTimeRange", []))
            );
    }

    getReportByTopic(topicName: string): Observable<Report[]> {
        let url = this.byTopicUrl + topicName;
        return this.http
            .get<Report[]>(url)
            .pipe(catchError(this.handleError("getReportByTopic", [])));
    }

    generateCsv(topicName: string, from: number, to: number): void {
        let csvDownloadUrl =
            this.csvReportUrl +
            "?from=" +
            from +
            "&to=" +
            to +
            "&topicName=" +
            topicName;
        window.open(csvDownloadUrl, "_blank");
    }

    getTopics(): Observable<string[]> {
        return this.http.get<string[]>(this.topicsUrl)
            .pipe(
                catchError(this.handleError('getTopics', []))
            );
        // TODO uncomment this once real API is available
        // return of(["NSI_1_2", "NSI_1_3", "NSI_1_4"]).pipe(
        //     catchError(this.handleError("getTopics", []))
        // );
    }

    convertToChartModel(reports: Report[]): ChartModel[] {
        let chartModelArr: ChartModel[] = [];

        for (let item of reports) {
            let dataPoints: ChartDTO[] = [];

            for (let dataItem of item.data) {
                let chartDTO: ChartDTO = {
                    // x: dataItem.timestamp,
                    label: this.datePipe.transform(
                        dataItem.timestamp,
                        "HH:mm:ss"
                    ),
                    y: dataItem.value,
                };

                dataPoints.push(chartDTO);
            }

            let chartModel: ChartModel = {
                type: "line",
                name: item.topicName,
                showInLegend: true,
                yValueFormatString: "#,###",
                dataPoints: dataPoints,
            };

            chartModelArr.push(chartModel);
        }

        return chartModelArr;
    }

    convertToHighChartModel(reportSeries: Report[]): [] {
        let series = [];
        for (let reportItem of reportSeries) {
            let twoDArray: any[] = [];

            for (let dataItem of reportItem.data) {
                let dataPoints = [dataItem.timestamp, dataItem.value];
                twoDArray.push(dataPoints);
            }

            let seriesItem: any = {
                type: "line",
                name: reportItem.topicName,
                data: twoDArray,
            };

            series.push(seriesItem);
        }
        return series as [];
    }
}
