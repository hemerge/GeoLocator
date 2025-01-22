
# GeoLocator API

GeoLocator API provides services for geolocation-based queries, allowing users to perform reverse geocoding by latitude and longitude.

## Table of Contents
- [Prerequisites](#prerequisites)
- [Running the Project](#running-the-project)
- [API Endpoints](#api-endpoints)
  - [Get Address from Latitude and Longitude](#get-address-from-latitude-and-longitude)
  - [Bulk Reverse Geocode](#bulk-reverse-geocode)
- [Error Responses](#error-responses)

## Project Setup

### Cloning the Repository
First, clone the repository to your local machine:
```
git clone https://github.com/your-username/GeoLocator.git
cd GeoLocator
```

### Prerequisites

Ensure you have the following software installed:

1. **Java 21**
2. **Maven**
3. **Git**

You can check if you have the necessary tools installed by running:
```java -version
mvn -version
```

### Running the Project

To run the project locally, use the following steps:

1. **Build the Project:**
   Compile and package the application using Maven:
   ```
   mvn clean install
   ```

2. **Run the Application:**
   Start the Spring Boot application:
   ```
   mvn spring-boot:run
   ```

   The application will start running on [http://localhost:8080](http://localhost:8080).

3. **Access the Application:**
   Once the application is up and running, use the endpoints described below to interact with the service.

---

## API Endpoints

### 1. **Get Address from Latitude and Longitude**

**Endpoint:**
```
GET /api/geolocator/reverse-geocode
```

**Query Parameters:**
- `latitude` (required) - Latitude for reverse geocoding.
- `longitude` (required) - Longitude for reverse geocoding.

**Example Request:**
```
GET http://localhost:8080/api/geolocator/reverse-geocode?latitude=37.7749&longitude=-122.4194
```

**Response:**
- **200 OK** - Returns a `GeoLocationDTO` with address information.

Example Response:
```json
{
    "address": {
        "road": "Market St",
        "city": "San Francisco",
        "country": "United States",
        "postcode": "94103"
    }
}
```

### 2. **Bulk Reverse Geocode**

**Endpoint:**
```
POST /api/geolocator/bulk-reverse-geocode
```

**Request Body:**
```json
[
    [37.7749, -122.4194],
    [40.7306, -73.9352]
]
```

**Response:**
- **200 OK** - Returns a list of addresses corresponding to the latitude-longitude pairs.

Example Response:
```json
[
    {
        "address": {
            "road": "Market St",
            "city": "San Francisco",
            "country": "United States",
            "postcode": "94103"
        }
    },
    {
        "address": {
            "road": "Bedford Ave",
            "city": "Brooklyn",
            "country": "United States",
            "postcode": "11211"
        }
    }
]
```

---

## Error Responses

The following error responses are returned by the API:

### 1. **Invalid Latitude and Longitude**
If the provided latitude or longitude is invalid or outside the acceptable ranges, the service will return a `400 Bad Request` with an error message.

**Example Response:**
```json
{
    "error": "Invalid latitude and/or longitude values."
}
```

### 2. **Geolocation Data Not Found**
If the API cannot find geolocation data for the given coordinates, the service will return a `404 Not Found`.

**Example Response:**
```json
{
    "error": "No geolocation data found for the provided coordinates."
}
```

### 3. **Server Error**
If there is an internal issue while processing the request, a `500 Internal Server Error` is returned.

**Example Response:**
```json
{
    "error": "An error occurred while fetching geolocation data."
}
```
```
---