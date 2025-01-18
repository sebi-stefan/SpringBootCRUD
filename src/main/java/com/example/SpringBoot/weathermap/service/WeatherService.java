package com.example.SpringBoot.weathermap.service;

import com.example.SpringBoot.weathermap.model.AirPollutionData;
import com.example.SpringBoot.weathermap.model.ForecastData;
import com.example.SpringBoot.weathermap.model.WeatherData;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.beans.factory.annotation.Value;

@Service
public class WeatherService {
    private final WebClient webClient;
    private final String apiKey;

    public WeatherService(
            WebClient.Builder webClientBuilder,
            @Value("${openweathermap.api.key}") String apiKey
    ) {
        this.webClient = webClientBuilder.baseUrl("https://api.openweathermap.org").build();
        this.apiKey = apiKey;
    }

    public WeatherData getCurrentWeather(String city) {
        try {
            return webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/data/2.5/weather")
                            .queryParam("q", city)
                            .queryParam("appid", apiKey)
                            .queryParam("units", "metric")
                            .build())
                    .retrieve()
                    .bodyToMono(WeatherData.class)
                    .block();
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Error fetching current weather: " + e.getMessage(), e);
        }
    }

    public ForecastData getWeatherForecast(String city) {
        try {
            // First, get coordinates for the city
            WeatherData currentWeather = getCurrentWeather(city);

            return webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/data/3.0/onecall")  // Note the updated path to 3.0
                            .queryParam("lat", currentWeather.getCoord().getLat())
                            .queryParam("lon", currentWeather.getCoord().getLon())
                            .queryParam("exclude", "current,minutely,hourly,alerts")
                            .queryParam("appid", apiKey)
                            .queryParam("units", "metric")
                            .build())
                    .retrieve()
                    .bodyToMono(ForecastData.class)
                    .block();
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Error fetching forecast: " + e.getMessage(), e);
        }
    }

    public AirPollutionData getAirPollution(String city) {
        // First, get coordinates for the city
        WeatherData currentWeather = getCurrentWeather(city);

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/air_pollution")
                        .queryParam("lat", currentWeather.getCoord().getLat())
                        .queryParam("lon", currentWeather.getCoord().getLon())
                        .queryParam("appid", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(AirPollutionData.class)
                .block();
    }
}