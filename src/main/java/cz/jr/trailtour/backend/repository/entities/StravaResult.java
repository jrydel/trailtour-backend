package cz.jr.trailtour.backend.repository.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Jiří Rýdel on 5/5/20, 3:04 PM
 */
public class StravaResult extends TrailtourResult {

    @JsonProperty(value = "activityId")
    private Long activityId;
    @JsonProperty(value = "date")
    private String date;
    @JsonProperty(value = "position")
    private Integer position;

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}
