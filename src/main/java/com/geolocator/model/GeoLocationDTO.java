package com.geolocator.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoLocationDTO {

    @JsonProperty("place_id")
    private Long placeId;

    @JsonProperty("lat")
    private String latitude;

    @JsonProperty("lon")
    private String longitude;

    @JsonProperty("display_name")
    private String displayName;

    // Address details nested JSON
    @JsonProperty("address")
    private AddressDTO address;

    // Getters and Setters

    public static class AddressDTO {
        @JsonProperty("building")
        private String building;

        @JsonProperty("city")
        private String city;

        @JsonProperty("country")
        private String country;

        // Getters and Setters
    }
}