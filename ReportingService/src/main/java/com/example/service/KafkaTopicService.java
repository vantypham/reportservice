package com.example.service;

import com.example.domain.KafkaTopic;

import java.util.Set;

public interface KafkaTopicService {
    Set<String> getAllTopics();

    Set<KafkaTopic> getAllTopicsWithIndices();
}
