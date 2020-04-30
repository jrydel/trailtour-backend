package cz.jr.trailtour.backend.repository.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

/**
 * Created by Jiří Rýdel on 4/29/20, 3:02 PM
 */
public class Result {
    @JsonProperty(value = "segmentId")
    private long segmentId;
    @JsonProperty(value = "athleteId")
    private long athleteId;
    @JsonProperty(value = "activityId")
    private long activityId;
    @JsonProperty(value = "position")
    private int position;
    @JsonProperty(value = "date")
    private LocalDate date;
    @JsonProperty(value = "time")
    private long time;

    public long getSegmentId() {
        return segmentId;
    }

    public void setSegmentId(long stageId) {
        this.segmentId = stageId;
    }

    public long getAthleteId() {
        return athleteId;
    }

    public void setAthleteId(long athleteId) {
        this.athleteId = athleteId;
    }

    public long getActivityId() {
        return activityId;
    }

    public void setActivityId(long activityId) {
        this.activityId = activityId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
