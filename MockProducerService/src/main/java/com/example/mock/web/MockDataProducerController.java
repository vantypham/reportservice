package com.example.mock.web;

import com.example.mock.service.MockDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/data")
public class MockDataProducerController {
    @Autowired
    MockDataService service;
    @GetMapping
    public ResponseEntity<?> genData() {
        service.genDataAndPutToKafka();
        return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
    }


    @Scheduled(fixedRate = 21000)
    public void generateData() {
        this.genData();
    }
    @Scheduled(fixedRate = 31000)
    public void generateData2() {
        service.genDataAndPutToKafka2();
    }
    @Scheduled(fixedRate = 45000)
    public void generateData3() {
        service.genDataAndPutToKafka3();
    }
}
