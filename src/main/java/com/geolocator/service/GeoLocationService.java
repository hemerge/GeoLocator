package com.geolocator.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GeoLocationService {

    private final WebClient webClient;

    public GeoLocationService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("https://nominatim.openstreetmap.org")
                .defaultHeader("User-Agent", "GeoLocatorApp/1.0")
                .build();
    }

    public Mono<String> getAddressFromLatLon(double latitude, double longitude) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/reverse")
                        .queryParam("format", "json")
                        .queryParam("lat", latitude)
                        .queryParam("lon", longitude)
                        .queryParam("addressdetails", 1)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(e -> Mono.just("Error fetching address: " + e.getMessage()));
    }

    public Flux<String> getAddressesFromLatLonBulk(List<double[]> coordinatesList) {
        List<Mono<String>> addressRequests = coordinatesList.stream()
                .map(coord -> getAddressFromLatLon(coord[0], coord[1]))
                .collect(Collectors.toList());
        return Flux.merge(addressRequests);
    }
}
