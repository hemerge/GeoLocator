package com.geolocator.service;

import com.geolocator.model.GeoLocationDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GeoLocationService {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    @Value("${geolocator.api.url}")
    private String apiUrl;

    public GeoLocationService(ObjectMapper objectMapper) {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = objectMapper;
    }

    // Method to get a single address as a DTO
    public Optional<GeoLocationDTO> getAddressFromLatLon(double latitude, double longitude) {
        try {
            String url = String.format("%s/reverse?format=json&lat=%s&lon=%s&addressdetails=1", 
                    apiUrl, latitude, longitude);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("User-Agent", "GeoLocatorApp/1.0")
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                GeoLocationDTO geoLocation = objectMapper.readValue(response.body(), GeoLocationDTO.class);
                return Optional.of(geoLocation);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    // Method to get multiple addresses as DTOs
    public List<Optional<GeoLocationDTO>> getAddressesFromLatLonBulk(List<double[]> coordinatesList) {
        List<Optional<GeoLocationDTO>> result = new ArrayList<>();
        for (double[] coordinates : coordinatesList) {
            double latitude = coordinates[0];
            double longitude = coordinates[1];
            result.add(getAddressFromLatLon(latitude, longitude));
        }
        return result;
    }
}