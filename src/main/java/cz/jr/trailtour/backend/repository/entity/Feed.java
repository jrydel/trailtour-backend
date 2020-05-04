package cz.jr.trailtour.backend.repository.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import cz.jr.trailtour.backend.config.Constants;

import java.time.LocalDateTime;

/**
 * Created by Jiří Rýdel on 5/2/20, 1:44 PM
 */
public class Feed {

    @JsonProperty(value = "created")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_FORMAT)
    private LocalDateTime created;
    @JsonProperty(value = "activityId")
    private Long activityId;
    @JsonProperty(value = "athleteId")
    private Long athleteId;
    @JsonProperty(value = "athleteName")
    private String athleteName;
    @JsonProperty(value = "stageId")
    private Long stageId;
    @JsonProperty(value = "stageNumber")
    private int stageNumber;
    @JsonProperty(value = "stageName")
    private String stageName;
    @JsonProperty(value = "stageCountry")
    private String stageCountry;
    @JsonProperty(value = "time")
    private int time;
    @JsonProperty(value = "position")
    private int position;

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Long getAthleteId() {
        return athleteId;
    }

    public void setAthleteId(Long athleteId) {
        this.athleteId = athleteId;
    }

    public String getAthleteName() {
        return athleteName;
    }

    public void setAthleteName(String athleteName) {
        this.athleteName = athleteName;
    }

    public Long getStageId() {
        return stageId;
    }

    public void setStageId(Long stageId) {
        this.stageId = stageId;
    }

    public int getStageNumber() {
        return stageNumber;
    }

    public void setStageNumber(int stageNumber) {
        this.stageNumber = stageNumber;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public String getStageCountry() {
        return stageCountry;
    }

    public void setStageCountry(String stageCountry) {
        this.stageCountry = stageCountry;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
