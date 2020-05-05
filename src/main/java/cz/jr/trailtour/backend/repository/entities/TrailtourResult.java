package cz.jr.trailtour.backend.repository.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Jiří Rýdel on 5/5/20, 4:50 PM
 */
public class TrailtourResult {

    @JsonProperty(value = "time")
    private Integer time;
    @JsonProperty(value = "points")
    private Double points;

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Double getPoints() {
        return points;
    }

    public void setPoints(Double points) {
        this.points = points;
    }
}
