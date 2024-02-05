package com.example.service.impl;

import com.example.domain.KafkaTopic;
import com.example.service.KafkaTopicService;
import com.example.repository.KafkaTopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class KafkaTopicServiceImpl implements KafkaTopicService {
    private final KafkaTopicRepository kafkaTopicRepository;

    @Autowired
    public KafkaTopicServiceImpl(KafkaTopicRepository kafkaTopicRepository) {
        this.kafkaTopicRepository = kafkaTopicRepository;
    }

    @Override
    public Set<String> getAllTopics() {
        return kafkaTopicRepository.findAll().stream().map(KafkaTopic::getName).collect(Collectors.toSet());
    }

    @Override
    public Set<KafkaTopic> getAllTopicsWithIndices() {
        return new HashSet<>(kafkaTopicRepository.findAll());
    }
}
