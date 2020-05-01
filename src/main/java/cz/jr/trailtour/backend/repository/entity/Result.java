package cz.jr.trailtour.backend.repository.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import cz.jr.trailtour.backend.config.Constants;

import java.time.LocalDate;

/**
 * Created by Jiří Rýdel on 4/29/20, 3:02 PM
 */
public class Result {

    @JsonProperty(value = "stageId")
    private Long stageId;
    @JsonProperty(value = "athlete")
    private Athlete athlete;
    @JsonProperty(value = "activityId")
    private Long activityId;
    @JsonProperty(value = "date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_FORMAT)
    private LocalDate date;
    @JsonProperty(value = "time")
    private Integer time;
    @JsonProperty(value = "position")
    private Integer position;
    @JsonProperty(value = "pointsStrava")
    private Double pointsStrava;

    public Long getStageId() {
        return stageId;
    }

    public void setStageId(Long stageId) {
        this.stageId = stageId;
    }

    public Athlete getAthlete() {
        return athlete;
    }

    public void setAthlete(Athlete athlete) {
        this.athlete = athlete;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Double getPointsStrava() {
        return pointsStrava;
    }

    public void setPointsStrava(Double pointsStrava) {
        this.pointsStrava = pointsStrava;
    }
}
