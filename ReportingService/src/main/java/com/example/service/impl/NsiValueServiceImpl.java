package com.example.service.impl;

import com.example.domain.KafkaTopic;
import com.example.domain.NSIValue;
import com.example.dto.NsiReportDto;
import com.example.dto.NsiValueDto;
import com.example.repository.NsiValueRepository;
import com.example.service.NsiValueService;
import com.example.repository.KafkaTopicRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class NsiValueServiceImpl implements NsiValueService {

    public static final String TOPIC_NAME_SEPARATOR = "_";
    private final NsiValueRepository nsiValueRepository;
    private final KafkaTopicRepository kafkaTopicRepository;

    Comparator<NsiValueDto> timestampComparator = Comparator.comparing(NsiValueDto::getTimestamp);
    @Autowired
    public NsiValueServiceImpl(NsiValueRepository nsiValueRepository, KafkaTopicRepository kafkaTopicRepository) {
        this.nsiValueRepository = nsiValueRepository;
        this.kafkaTopicRepository = kafkaTopicRepository;
    }

    @Override
    public NsiReportDto getByTopicName(String topicName) {
        return kafkaTopicRepository.findById(topicName).map(kafkaTopic -> {
            Set<NSIValue> values = nsiValueRepository.findByTopic(kafkaTopic);
            NsiReportDto report = new NsiReportDto();
            report.setTopicName(kafkaTopic.getName());
            report.setData(values.stream().map(v -> new NsiValueDto(v.getId(), v.getValue(), v.getTimestamp())).sorted(timestampComparator).collect(Collectors.toCollection(LinkedHashSet::new)));
            return report;
        }).orElseGet(NsiReportDto::new);
    }

    @Override
    public void getByTopicName(String topicName, PrintWriter writer) {
        nsiReportDtoToCsv(getByTopicName(topicName), writer);
    }

    @Override
    public Set<NsiReportDto> getByDuration(Long from, Long to) {
        Set<NSIValue> values = nsiValueRepository.findByTimestampBetween(from, to);
        return values.stream()
                .map(v -> new NsiValueDto(v.getId(), v.getValue(), v.getTimestamp(), v.getTopic().getName()))
                .collect(Collectors.groupingBy(NsiValueDto::getTopic, Collectors.toSet()))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
                .map(e -> new NsiReportDto(e.getKey(), e.getValue().stream().sorted(timestampComparator).collect(Collectors.toCollection(LinkedHashSet::new))))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public void getByDuration(Long from, Long to, PrintWriter writer) {
        nsiReportDtoSetToCsv(getByDuration(from, to), writer);
    }

    @Override
    public NsiReportDto getByTopicNameAndDuration(String topicName, Long from, Long to) {
        return kafkaTopicRepository.findById(topicName).map(kafkaTopic -> {
            Set<NSIValue> values = nsiValueRepository.findByTopicNameAndTimestampBetween(topicName, from, to);
            NsiReportDto report = new NsiReportDto();
            report.setTopicName(kafkaTopic.getName());
            report.setData(values.stream().map(v -> new NsiValueDto(v.getId(), v.getValue(), v.getTimestamp())).sorted(timestampComparator).collect(Collectors.toCollection(LinkedHashSet::new)));
            return report;
        }).orElseGet(NsiReportDto::new);
    }

    @Override
    public void getByTopicNameAndDuration(String topicName, Long from, Long to, PrintWriter writer) {
        nsiReportDtoToCsv(getByTopicNameAndDuration(topicName, from, to), writer);
    }

    @Override
    public NSIValue save(Double value, Long timestamp, String topicName) {
        final KafkaTopic kafkaTopic;
        Optional<KafkaTopic> opKafkaTopicById = kafkaTopicRepository.findById(topicName);
        if (opKafkaTopicById.isPresent()) {
            kafkaTopic = opKafkaTopicById.get();
        } else {
            int fistIndex = 0;
            int secondIndex = 0;
            String[] parts = topicName.split(TOPIC_NAME_SEPARATOR);
            if (parts.length == 3) {
                fistIndex = Integer.parseInt(parts[1]);
                secondIndex = Integer.parseInt(parts[2]);
            }
            kafkaTopic = kafkaTopicRepository.save(new KafkaTopic(topicName, fistIndex, secondIndex));
        }

        NSIValue nsiValue = new NSIValue();
        nsiValue.setValue(value);
        nsiValue.setTimestamp(timestamp);
        nsiValue.setTopic(kafkaTopic);
        return nsiValueRepository.save(nsiValue);
    }

    @Override
    public Set<NsiReportDto> getReport(String topicName, Long from, Long to) {
        Set<NsiReportDto> result = new HashSet<>();
        if (to == null) {
            to = System.currentTimeMillis();
        }

        if (StringUtils.isNotEmpty(topicName) && from == null) {
            result.add(getByTopicName(topicName));
        } else if (StringUtils.isEmpty(topicName) && from != null) {
            return getByDuration(from, to);
        } else if (StringUtils.isNotEmpty(topicName)) {
            result.add(getByTopicNameAndDuration(topicName, from, to));
        }
        return result;
    }

    @Override
    public Set<NsiReportDto> getReportMultipleTopics(String[] topicNames, Long from, Long to) {
        Set<NsiReportDto> result = new HashSet<>();
        for (int i = 0; i < topicNames.length; i++) {
            Set<NsiReportDto> set = getReport(topicNames[i], from, to);
            result.addAll(set);
        }

//        if (to == null) {
//            to = System.currentTimeMillis();
//        }
//
//        if (from == null) {
//            result.add(getByTopicName(topicNames));
//        } else if (StringUtils.isEmpty(topicNames) && from != null) {
//            return getByDuration(from, to);
//        } else if (StringUtils.isNotEmpty(topicNames)) {
//            result.add(getByTopicNameAndDuration(topicName, from, to));
//        }
        return result;
    }

    @Override
    public void getCsvReport(String topicName, Long from, Long to, PrintWriter writer) {
        nsiReportDtoSetToCsv(getReport(topicName, from, to), writer);
    }

    private void nsiReportDtoToCsv(NsiReportDto nsiReportDto, PrintWriter writer) {
        try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
            csvPrinter.printRecord("Topic name", "Timestamp", "Value");
            Set<NsiValueDto> valuesSet = nsiReportDto.getData();
            Iterator<NsiValueDto> valuesItr = valuesSet.iterator();
            while (valuesItr.hasNext()) {
                NsiValueDto nsiValue = valuesItr.next();
                csvPrinter.printRecord(nsiReportDto.getTopicName(), nsiValue.getTimestamp(), nsiValue.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void nsiReportDtoSetToCsv(Set<NsiReportDto> set, PrintWriter writer){
        try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
            csvPrinter.printRecord("Topic name", "Timestamp", "Value");
            Iterator<NsiReportDto> reportsIterator = set.iterator();
            while(reportsIterator.hasNext()){
                NsiReportDto reportSet = reportsIterator.next();
                String topicName = reportSet.getTopicName();
                Set<NsiValueDto> valuesSet = reportSet.getData();
                Iterator<NsiValueDto> valuesItr = valuesSet.iterator();
                while(valuesItr.hasNext()){
                    NsiValueDto nsiValue = valuesItr.next();
                    csvPrinter.printRecord(topicName, nsiValue.getTimestamp(), nsiValue.getValue());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
