package cz.jr.trailtour.backend.repository.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.jr.trailtour.backend.config.Constants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Jiří Rýdel on 4/19/20, 1:22 PM
 */
@Entity
@Table(catalog = Constants.DATABASE_NAME, name = "stage")
public class Stage extends DatabaseEntity {

    @JsonProperty(value = "country")
    @Column(name = "country")
    private String country;
    @JsonProperty(value = "number")
    @Column(name = "number")
    private int number;
    @JsonProperty(value = "name")
    @Column(name = "name")
    private String name;
    @JsonProperty(value = "latitude")
    @Column(name = "type")
    private String type;
    @JsonProperty(value = "distance")
    @Column(name = "distance")
    private Double distance;
    @JsonProperty(value = "elevation")
    @Column(name = "elevation")
    private Integer elevation;
    @JsonProperty(value = "type")
    @Column(name = "latitude")
    private Double latitude;
    @JsonProperty(value = "longitude")
    @Column(name = "longitude")
    private Double longitude;

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
}
