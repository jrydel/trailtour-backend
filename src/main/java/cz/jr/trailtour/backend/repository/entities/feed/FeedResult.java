package cz.jr.trailtour.backend.repository.entities.feed;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import cz.jr.trailtour.backend.config.Constants;

import java.time.LocalDateTime;

/**
 * Created by Jiří Rýdel on 5/2/20, 1:44 PM
 */
public class FeedResult {

    @JsonProperty(value = "athlete")
    private FeedAthlete athlete;
    @JsonProperty(value = "stage")
    private FeedStage stage;
    @JsonProperty(value = "activityId")
    private long activityId;
    @JsonProperty(value = "time")
    private int time;
    @JsonProperty(value = "position")
    private int position;
    @JsonProperty(value = "created")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_FORMAT)
    private LocalDateTime created;

    public FeedAthlete getAthlete() {
        return athlete;
    }

    public void setAthlete(FeedAthlete athlete) {
        this.athlete = athlete;
    }

    public FeedStage getStage() {
        return stage;
    }

    public void setStage(FeedStage stage) {
        this.stage = stage;
    }

    public long getActivityId() {
        return activityId;
    }

    public void setActivityId(long activityId) {
        this.activityId = activityId;
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

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
}
