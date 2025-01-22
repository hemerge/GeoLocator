# GeoLocator - Spring Boot Application

GeoLocator is a backend application built using Spring Boot that provides geolocation functionalities. It allows fetching latitude and longitude from a city name and performing reverse geocoding to get a city's address from its geographical coordinates. It supports both single and bulk queries with centralized error handling.

---

## Key Features
- Fetch latitude and longitude by city name.
- Reverse geocode (get address from latitude and longitude).
- Bulk reverse geocode functionality.
- REST APIs developed using Spring Boot.
- Reactive programming using WebFlux.

---

## Project Structure

### **Java (Spring Boot)**

#### **1. `GeoLocationController`**
- **Purpose**: Handles API requests related to geolocation functionalities.
- **Endpoints**:
  - `GET /api/geolocator/reverse-geocode` - Reverse geocodes a single latitude and longitude.
  - `POST /api/geolocator/bulk-reverse-geocode` - Reverse geocodes multiple latitude-longitude pairs.
- **Code Snippet**:
```java
@RestController
@RequestMapping("/api/geolocator")
public class GeoLocationController {
    // Methods for reverse geocoding and bulk operations
}
```

---

#### **2. `GeoLocationService`**
- **Purpose**: Contains the business logic for interacting with OpenLayers API and processing geolocation data.
- **Methods**:
  - `getAddress(double latitude, double longitude)` - Fetches address for given latitude and longitude.
  - `bulkGetAddresses(List<double[]> coordinates)` - Fetches addresses for multiple coordinates.

---

#### **3. `GlobalExceptionHandler`**
- **Purpose**: Centralizes error handling for all API exceptions.
- **Features**:
  - Handles validation errors.
  - Returns structured error responses.
- **Example Response**:
```json
{
    "message": "Validation failed",
    "details": "latitude must be provided"
}
```

---

## API Documentation

### **1. Reverse Geocode a Single Location**
- **Endpoint**: `GET /api/geolocator/reverse-geocode`
- **Query Parameters**:
  - `latitude` (double): Latitude of the location.
  - `longitude` (double): Longitude of the location.
- **Example CURL**:
```bash
curl -X GET "http://localhost:8080/api/geolocator/reverse-geocode?latitude=40.7128&longitude=-74.0060"
```
- **Response**:
```json
{
    "address": "New York, NY, USA"
}
```

### **2. Bulk Reverse Geocode**
- **Endpoint**: `POST /api/geolocator/bulk-reverse-geocode`
- **Payload**: JSON array of latitude-longitude pairs.
- **Example CURL**:
```bash
curl -X POST "http://localhost:8080/api/geolocator/bulk-reverse-geocode" \
-H "Content-Type: application/json" \
-d '[ [40.7128, -74.0060], [37.7749, -122.4194] ]'
```
- **Response**:
```json
[
    {"latitude": 40.7128, "longitude": -74.0060, "address": "New York, NY, USA"},
    {"latitude": 37.7749, "longitude": -122.4194, "address": "San Francisco, CA, USA"}
]
```

---

### **Error Responses**
#### Missing or Invalid Parameters:
- **Response**:
```json
{
    "message": "Validation failed",
    "details": "latitude must be provided"
}
```

#### Internal Server Error:
- **Response**:
```json
{
    "message": "Internal server error",
    "details": "Unexpected error occurred"
}
```

---

## Getting Started

### Prerequisites
- **Java 8 or higher**
- **Maven**

### Steps to Run the Project

1. Navigate to the backend directory.
2. Install dependencies:
   ```bash
   mvn clean install
   ```
3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

---

## Technology Stack

- **Backend**: Java 8, Spring Boot (WebFlux), Maven
- **API Integration**: OpenLayers
- **Error Handling**: Centralized Exception Handling (Spring ControllerAdvice)

---

## Contributing
Contributions are welcome! Feel free to open an issue or submit a pull request with your improvements or bug fixes.

---

## License
This project is licensed under the MIT License. See the `LICENSE` file for details.

