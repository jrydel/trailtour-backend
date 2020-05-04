package cz.jr.trailtour.backend.repository.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import cz.jr.trailtour.backend.config.Constants;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by Jiří Rýdel on 4/29/20, 3:02 PM
 */
public class Result {

    @JsonProperty(value = "stageId")
    private Long stageId;
    @JsonProperty(value = "athlete")
    private Athlete athlete;
    @JsonProperty(value = "strava")
    private Strava strava;
    @JsonProperty(value = "points")
    private BigDecimal points;
    @JsonProperty(value = "pointsTrailtour")
    private BigDecimal pointsTrailtour;

    @JsonProperty(value = "updated")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_FORMAT)
    private LocalDateTime updated;
    @JsonProperty(value = "created")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_FORMAT)
    private LocalDateTime created;

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

    public Strava getStrava() {
        return strava;
    }

    public void setStrava(Strava strava) {
        this.strava = strava;
    }

    public BigDecimal getPoints() {
        return points;
    }

    public void setPoints(BigDecimal points) {
        this.points = points;
    }

    public BigDecimal getPointsTrailtour() {
        return pointsTrailtour;
    }

    public void setPointsTrailtour(BigDecimal pointsTrailtour) {
        this.pointsTrailtour = pointsTrailtour;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
}
