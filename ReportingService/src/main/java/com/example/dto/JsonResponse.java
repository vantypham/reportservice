package com.example.dto;

//import com.fasterxml.jackson.annotation.JsonIgnore;

//import jakarta.validation.OverridesAttribute.List;
//import lombok.AllArgsConstructor;
import lombok.Data;
//import lombok.NoArgsConstructor;
//import java.time.LocalDate;
import java.util.ArrayList;

@Data
public class JsonResponse {
    private String status;
    private String message;
    private ArrayList<MockedData> data;

    public JsonResponse(String status, String message, ArrayList<MockedData> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

}
