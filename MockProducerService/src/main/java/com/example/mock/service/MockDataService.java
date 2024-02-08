package com.example.mock.service;

import com.example.dto.ReportDataObject;
import com.example.mock.kafka.MessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
public class MockDataService {

    MessageProducer messageProducer;
    @Autowired
    public MockDataService(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    public void genDataAndPutToKafka() {
        messageProducer.sendMessageTopic1(getReportDataObject());
    }
    public void genDataAndPutToKafka2() {
        messageProducer.sendMessageTopic2(getReportDataObject2());
    }
    public void genDataAndPutToKafka3() {
        messageProducer.sendMessageTopic3(getReportDataObject3());
    }

    private ReportDataObject getReportDataObject() {
        String id = randomId();
        double value = randomValue();
        ReportDataObject object = new ReportDataObject(id, value, System.currentTimeMillis());
        return object;
    }
    private ReportDataObject getReportDataObject2() {
        String id = randomId();
        double value = randomValue2();
        ReportDataObject object = new ReportDataObject(id, value, System.currentTimeMillis());
        return object;
    }
    private ReportDataObject getReportDataObject3() {
        String id = randomId();
        double value = randomValue3();
        ReportDataObject object = new ReportDataObject(id, value, System.currentTimeMillis());
        return object;
    }

    private String randomId() {
        return UUID.randomUUID().toString().replace("-", "");
    }
    private double randomValue() {
        double rangeMin = 0.8;
        double rangeMax = 1.0;
        Random r = new Random();
        return rangeMin + (rangeMax - rangeMin) * r.nextDouble();
    }
    private double randomValue2() {
        double rangeMin = 0.6;
        double rangeMax = 1.0;
        Random r = new Random();
        return rangeMin + (rangeMax - rangeMin) * r.nextDouble();
    }
    private double randomValue3() {
        double rangeMin = 0.3;
        double rangeMax = 0.6;
        Random r = new Random();
        return rangeMin + (rangeMax - rangeMin) * r.nextDouble();
    }

}
