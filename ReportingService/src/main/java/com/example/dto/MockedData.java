package com.example.dto;

//import jakarta.validation.OverridesAttribute.List;
//import lombok.AllArgsConstructor;
import lombok.Data;
//import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.ArrayList;

@Data
// @NoArgsConstructor
// @AllArgsConstructor
public class MockedData {
    private LocalDate date;
    private String time;
    private String topic;
    private double average;

    public MockedData(LocalDate date, String time, String topic, double average) {
        this.average = average;
        this.topic = topic;
        this.time = time;
        this.date = date;
    }

    public static JsonResponse generateReport() {
        ArrayList<MockedData> mockedDataList = new ArrayList<>();
        mockedDataList.add(new MockedData(LocalDate.of(2021, 1, 1), "10:00", "NSI_1_3", 0.1));
        mockedDataList.add(new MockedData(LocalDate.of(2021, 1, 1), "10:00", "NSI_1_8", 0.1));
        mockedDataList.add(new MockedData(LocalDate.of(2021, 1, 1), "10:00", "NSI_2_2", 0.1));
        mockedDataList.add(new MockedData(LocalDate.of(2021, 1, 1), "10:00", "NSI_3_1", 0.98));
        mockedDataList.add(new MockedData(LocalDate.of(2021, 1, 1), "10:00", "NSI_3_2", 0.87));
        mockedDataList.add(new MockedData(LocalDate.of(2021, 1, 1), "10:00", "NSI_3_3", 0.76));

        return new JsonResponse("Success", "Data fetched successfuly", mockedDataList);
    }

}
