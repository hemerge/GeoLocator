package com.geolocator.controller;

import com.geolocator.service.GeoLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController
@RequestMapping("/api/geolocator")
public class GeoLocationController {

    @Autowired
    private GeoLocationService geoLocationService;

    @GetMapping("/reverse-geocode")
    public Mono<ResponseEntity<String>> getAddress(
            @RequestParam double latitude,
            @RequestParam double longitude) {
        return geoLocationService.getAddressFromLatLon(latitude, longitude)
                .map(ResponseEntity::ok);
    }

    @PostMapping("/bulk-reverse-geocode")
    public ResponseEntity<Flux<String>> getAddresses(
            @RequestBody @NotEmpty List<double[]> coordinatesList) {
        Flux<String> addresses = geoLocationService.getAddressesFromLatLonBulk(coordinatesList);
        return ResponseEntity.ok(addresses);
    }
}
