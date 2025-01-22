package com.geolocator.controller;

import com.geolocator.model.GeoLocationDTO;
import com.geolocator.service.GeoLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/geolocator")
public class GeoLocationController {

    @Autowired
    private GeoLocationService geoLocationService;

    @GetMapping("/reverse-geocode")
    public ResponseEntity<GeoLocationDTO> getAddress(
            @RequestParam double latitude,
            @RequestParam double longitude) {
        Optional<GeoLocationDTO> optionalGeoLocationDTO = geoLocationService.getAddressFromLatLon(latitude, longitude);
        return optionalGeoLocationDTO
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/bulk-reverse-geocode")
    public ResponseEntity<List<GeoLocationDTO>> getAddresses(
            @RequestBody @NotEmpty List<double[]> coordinatesList) {
        List<GeoLocationDTO> addresses = coordinatesList.stream()
                .map(coord -> geoLocationService.getAddressFromLatLon(coord[0], coord[1]))
                .flatMap(Optional::stream)
                .collect(Collectors.toList());

        if (addresses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(addresses);
    }
}