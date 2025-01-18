package com.example.SpringBoot.weathermap.controller;

import com.example.SpringBoot.weathermap.exceptions.WeatherApiException;
import com.example.SpringBoot.weathermap.model.ForecastData;
import com.example.SpringBoot.weathermap.model.WeatherData;
import com.example.SpringBoot.weathermap.service.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {
    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/current")
    public ResponseEntity<?> getCurrentWeather(@RequestParam String city) {
        try {
            WeatherData weatherData = weatherService.getCurrentWeather(city);
            return ResponseEntity.ok(weatherData);
        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .body("Error fetching current weather: " + e.getMessage());
        }
    }

    @GetMapping("/forecast")
    public ResponseEntity<?> getWeatherForecast(@RequestParam String city) {
        try {
            ForecastData forecastData = weatherService.getWeatherForecast(city);
            return ResponseEntity.ok(forecastData);
        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .body("Error fetching forecast: " + e.getMessage());
        }
    }

    @ExceptionHandler(WeatherApiException.class)
    public ResponseEntity<?> handleWeatherApiException(WeatherApiException e) {
        return ResponseEntity
                .internalServerError()
                .body(e.getMessage());
    }
}