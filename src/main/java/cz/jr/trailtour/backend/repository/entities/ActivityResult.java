package cz.jr.trailtour.backend.repository.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Jiří Rýdel on 5/19/20, 12:15 AM
 */
public class ActivityResult {

    @JsonProperty(value = "position")
    private Integer position;
    @JsonProperty(value = "trailtourPosition")
    private Integer trailtourPosition;
    @JsonProperty(value = "trailtourTime")
    private Integer trailtourTime;
    @JsonProperty(value = "points")
    private Double points;
    @JsonProperty(value = "trailtourPoints")
    private Double trailtourPoints;

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getTrailtourPosition() {
        return trailtourPosition;
    }

    public void setTrailtourPosition(Integer trailtourPosition) {
        this.trailtourPosition = trailtourPosition;
    }

    public Integer getTrailtourTime() {
        return trailtourTime;
    }

    public void setTrailtourTime(Integer trailtourTime) {
        this.trailtourTime = trailtourTime;
    }

    public Double getPoints() {
        return points;
    }

    public void setPoints(Double points) {
        this.points = points;
    }

    public Double getTrailtourPoints() {
        return trailtourPoints;
    }

    public void setTrailtourPoints(Double trailtourPoints) {
        this.trailtourPoints = trailtourPoints;
    }
}
