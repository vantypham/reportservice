
package com.example.controller;

import com.example.dto.API;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = "http://localhost:3000")
public class APIsController {

    List<API> apis = new ArrayList<>();

    public APIsController() {
        apis.addAll(List.of(
                new API("1", "Weather API", "https://weather.com/api", "Provides weather data", "FREE"),
                new API("2", "Stock API", "https://stock.com/api", "Provides stock data", "NEW"),
                new API("3", "News API", "https://news.com/api", "Provides news data", "UNAVAILABLE"),
                new API("4", "Sports API", "https://sports.com/api", "Provides sports data", "UNCERTAIN"),
                new API("5", "Music API", "https://music.com/api", "Provides music data", "FREE"),
                new API("6", "Movie API", "https://movie.com/api", "Provides movie data", "NEW"),
                new API("7", "Travel API", "https://travel.com/api", "Provides travel data", "UNAVAILABLE"),
                new API("8", "Food API", "https://food.com/api", "Provides food data", "UNCERTAIN"),
                new API("9", "Health API", "https://health.com/api", "Provides health data", "FREE"),
                new API("10", "Education API", "https://education.com/api", "Provides education data", "NEW"),
                new API("11", "Finance API", "https://finance.com/api", "Provides finance data", "UNAVAILABLE"),
                new API("12", "Social API", "https://social.com/api", "Provides social data", "UNCERTAIN"),
                new API("13", "E-commerce API", "https://ecommerce.com/api", "Provides e-commerce data", "FREE"),
                new API("14", "Gaming API", "https://gaming.com/api", "Provides gaming data", "NEW"),
                new API("15", "Real Estate API", "https://realestate.com/api", "Provides real estate data", "UNAVAILABLE"),
                new API("16", "Automotive API", "https://automotive.com/api", "Provides automotive data", "UNCERTAIN"),
                new API("17", "Telecom API", "https://telecom.com/api", "Provides telecom data", "FREE"),
                new API("18", "Energy API", "https://energy.com/api", "Provides energy data", "NEW"),
                new API("19", "Environment API", "https://environment.com/api", "Provides environment data", "UNAVAILABLE"),
                new API("20", "Agriculture API", "https://agriculture.com/api", "Provides agriculture data", "UNCERTAIN")
        ));

    }

    @GetMapping("/apis")
    public List<API> getAPIs() {
        return apis;
    }

    @PutMapping("/apis/{id}")
    public API updateAPI(@PathVariable String id, @RequestBody API api) {

        return apis.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .map(a -> {
                    a.setName(api.getName());
                    a.setEndpoint(api.getEndpoint());
                    a.setDescription(api.getDescription());
                    a.setStatus(api.getStatus());
                    return a;
                })
                .orElse(null);
    }

    @PostMapping("/apis")
    public API createAPI(@RequestBody API api) {
        api.setId(apis.size() + 1 + "");
        apis.add(api);
        return api;
    }
}