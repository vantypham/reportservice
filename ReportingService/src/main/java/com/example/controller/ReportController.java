package com.example.controller;

import com.example.dto.NsiReportDto;
import com.example.service.KafkaTopicService;
import com.example.service.NsiValueService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/report")
@CrossOrigin
public class ReportController {

    private final NsiValueService nsiValueService;

    private final KafkaTopicService kafkaTopicService;

    @Autowired
    public ReportController(NsiValueService nsiValueService, KafkaTopicService kafkaTopicService) {
        this.nsiValueService = nsiValueService;
        this.kafkaTopicService = kafkaTopicService;
    }

    @Operation(summary = "Get list of kafka topics")
    @GetMapping("/topics")
    @ResponseStatus(HttpStatus.OK)
    public Set<String> topics() {
        return kafkaTopicService.getAllTopics();
    }

//    @Operation(summary = "Get NSI report from mock data")
//    @CrossOrigin
//    @GetMapping
//    @ResponseStatus(HttpStatus.OK)
//    public JsonResponse getReport(@RequestParam(value = "topicName", required = false) String topicName,
//                                  @RequestParam(value = "from", required = false) Long from,
//                                  @RequestParam(value = "to", required = false) Long to) {
//        return MockedData.generateReport();
//    }

    @Operation(summary = "Get NSI report by topic name and time period")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Set<NsiReportDto> getReport(@RequestParam(value = "topicName",
        required = false) String topicName,
                                       @RequestParam(value = "from", required = false) Long from,
                                       @RequestParam(value = "to", required = false) Long to) {
        return nsiValueService.getReport(topicName, from, to);
    }

    @Operation(summary = "Get NSI report by multiple topic name and time period")
    @GetMapping("/topicNames")
    @ResponseStatus(HttpStatus.OK)
    public Set<NsiReportDto> getReportMultipleTopics(@RequestParam(value = "topicNames",
            required = false) String[] topicNames,
                                       @RequestParam(value = "from", required = false) Long from,
                                       @RequestParam(value = "to", required = false) Long to) {
        System.out.println("topicNames=" + topicNames);
        return nsiValueService.getReportMultipleTopics(topicNames, from, to);
    }

    @Operation(summary = "Get NSI report by time period")
    @GetMapping("/time")
    @ResponseStatus(HttpStatus.OK)
    public Set<NsiReportDto> getByTime(@RequestParam("from") Long from, @RequestParam("to") Long to) {
        return nsiValueService.getByDuration(from, to);
    }

    @Operation(summary = "Get NSI report by topic name")
    @GetMapping("/topic/{topicName}")
    @ResponseStatus(HttpStatus.OK)
    public NsiReportDto getByTopicName(@PathVariable("topicName") String topicName) {
        return nsiValueService.getByTopicName(topicName);
    }

    @Operation(summary = "Get NSI report by topic name and time period")
    @GetMapping("/topic/{topicName}/time")
    @ResponseStatus(HttpStatus.OK)
    public NsiReportDto getByTimeAndTopic(@PathVariable("topicName") String topicName,
                                          @RequestParam("from") Long from, @RequestParam("to") Long to) {
        return nsiValueService.getByTopicNameAndDuration(topicName, from, to);
    }
}
