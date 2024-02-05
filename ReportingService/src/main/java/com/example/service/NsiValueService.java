package com.example.service;

import com.example.dto.NsiReportDto;
import com.example.domain.NSIValue;

import java.io.PrintWriter;
import java.util.Set;

public interface NsiValueService {
    NsiReportDto getByTopicName(String topicName);

    void getByTopicName(String topicName, PrintWriter writer);

    Set<NsiReportDto> getByDuration(Long from, Long to);

    void getByDuration(Long from, Long to, PrintWriter writer);

    NsiReportDto getByTopicNameAndDuration(String topicName, Long from, Long to);

    void getByTopicNameAndDuration(String topicName, Long from, Long to, PrintWriter writer);

    NSIValue save(Double value, Long timestamp, String topicName);

    Set<NsiReportDto> getReport(String topicName, Long from, Long to);

    void getCsvReport(String topicName, Long from, Long to, PrintWriter writer);
}
