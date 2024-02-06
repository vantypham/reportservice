export interface TopicFilter{
    startDateTime: number;
    endDateTime: number;
    actionType: 'GenerateReport' | 'DownloadCsv' | '';
    topicName: string,
    topicNames: string[]
}