package cz.jr.trailtour.backend.repository.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Jiří Rýdel on 4/29/20, 3:02 PM
 */
public class Result {

    @JsonProperty(value = "athlete")
    private Athlete athlete;
    @JsonProperty(value = "stravaResult")
    private StravaResult stravaResult;
    @JsonProperty(value = "trailtourResult")
    private TrailtourResult trailtourResult;

    public Athlete getAthlete() {
        return athlete;
    }

    public void setAthlete(Athlete athlete) {
        this.athlete = athlete;
    }

    public StravaResult getStravaResult() {
        return stravaResult;
    }

    public void setStravaResult(StravaResult stravaResult) {
        this.stravaResult = stravaResult;
    }

    public TrailtourResult getTrailtourResult() {
        return trailtourResult;
    }

    public void setTrailtourResult(TrailtourResult trailtourResult) {
        this.trailtourResult = trailtourResult;
    }
}
