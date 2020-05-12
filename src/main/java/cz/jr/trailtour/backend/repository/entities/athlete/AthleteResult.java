package cz.jr.trailtour.backend.repository.entities.athlete;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.jr.trailtour.backend.repository.entities.StravaResult;
import cz.jr.trailtour.backend.repository.entities.TrailtourResult;

/**
 * Created by Jiří Rýdel on 5/11/20, 12:13 PM
 */
public class AthleteResult {

    @JsonProperty(value = "stage")
    private AthleteStage stage;
    @JsonProperty(value = "stravaResult")
    private StravaResult stravaResult;
    @JsonProperty(value = "trailtourResult")
    private TrailtourResult trailtourResult;

    public AthleteStage getStage() {
        return stage;
    }

    public void setStage(AthleteStage stage) {
        this.stage = stage;
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
