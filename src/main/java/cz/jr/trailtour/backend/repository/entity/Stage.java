package cz.jr.trailtour.backend.repository.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Jiří Rýdel on 4/19/20, 1:22 PM
 */
public class Stage extends DatabaseEntity {

    @JsonProperty(value = "country")
    private String country;
    @JsonProperty(value = "number")
    private int number;
    @JsonProperty(value = "name")
    private String name;
    @JsonProperty(value = "type")
    private String type;
    @JsonProperty(value = "distance")
    private Double distance;
    @JsonProperty(value = "elevation")
    private Integer elevation;
    @JsonProperty(value = "latitude")
    private Double latitude;
    @JsonProperty(value = "longitude")
    private Double longitude;
    @JsonProperty(value = "trailtourUrl")
    private String trailtourUrl;
    @JsonProperty(value = "mapyczUrl")
    private String mapyczUrl;
    @JsonProperty(value = "activities")
    private Integer activities;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Integer getElevation() {
        return elevation;
    }

    public void setElevation(Integer elevation) {
        this.elevation = elevation;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getTrailtourUrl() {
        return trailtourUrl;
    }

    public void setTrailtourUrl(String trailtourUrl) {
        this.trailtourUrl = trailtourUrl;
    }

    public String getMapyczUrl() {
        return mapyczUrl;
    }

    public void setMapyczUrl(String mapyczUrl) {
        this.mapyczUrl = mapyczUrl;
    }

    public Integer getActivities() {
        return activities;
    }

    public void setActivities(Integer activities) {
        this.activities = activities;
    }
}
