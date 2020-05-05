package cz.jr.trailtour.backend.repository.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Jiří Rýdel on 4/19/20, 1:22 PM
 */
public class Stage {

    @JsonProperty(value = "number")
    private int number;
    @JsonProperty(value = "name")
    private String name;
    @JsonProperty(value = "url")
    private String url;
    @JsonProperty(value = "distance")
    private int distance;
    @JsonProperty(value = "elevation")
    private int elevation;
    @JsonProperty(value = "type")
    private String type;
    @JsonProperty(value = "stravaUrl")
    private String stravaUrl;
    @JsonProperty(value = "mapyczUrl")
    private String mapyczUrl;
    @JsonProperty(value = "activities")
    private int activities;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getElevation() {
        return elevation;
    }

    public void setElevation(int elevation) {
        this.elevation = elevation;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStravaUrl() {
        return stravaUrl;
    }

    public void setStravaUrl(String stravaUrl) {
        this.stravaUrl = stravaUrl;
    }

    public String getMapyczUrl() {
        return mapyczUrl;
    }

    public void setMapyczUrl(String mapyczUrl) {
        this.mapyczUrl = mapyczUrl;
    }

    public int getActivities() {
        return activities;
    }

    public void setActivities(int activities) {
        this.activities = activities;
    }
}
