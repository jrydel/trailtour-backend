package cz.jr.trailtour.backend.repository.entities.stage;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.jr.trailtour.backend.repository.entities.Athlete;

/**
 * Created by Jiří Rýdel on 5/7/20, 2:08 PM
 */
public class StageResult {

    @JsonProperty(value = "athlete")
    private Athlete athlete;
    @JsonProperty(value = "time")
    private Integer time;
    @JsonProperty(value = "timeTrailtour")
    private Integer timeTrailtour;

    public Athlete getAthlete() {
        return athlete;
    }

    public void setAthlete(Athlete athlete) {
        this.athlete = athlete;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getTimeTrailtour() {
        return timeTrailtour;
    }

    public void setTimeTrailtour(Integer timeTrailtour) {
        this.timeTrailtour = timeTrailtour;
    }
}
