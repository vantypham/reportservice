package com.example.mock.kafka;

import com.example.dto.ReportDataObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class MessageProducer {

//    @Autowired
//    private KafkaTemplate<String, ReportDataObject> greetingKafkaTemplate;

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Value(value = "${message.topic.name}")
    private String topicName;
    @Value(value = "${message.topic.name2}")
    private String topicName2;
    @Value(value = "${message.topic.name3}")
    private String topicName3;

    public void sendMessageTopic1(ReportDataObject obj) {
        extracted(obj, topicName);
    }

    public void sendMessageTopic2(ReportDataObject obj) {
        extracted(obj, topicName2);
    }

    public void sendMessageTopic3(ReportDataObject obj) {
        extracted(obj, topicName3);
    }

    private void extracted(ReportDataObject obj, String topicName) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String productString = objectMapper.writeValueAsString(obj);
            System.out.println("SENDER: TOPIC=" + topicName + " - DATA=" + productString);
            kafkaTemplate.send(topicName, productString);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}
