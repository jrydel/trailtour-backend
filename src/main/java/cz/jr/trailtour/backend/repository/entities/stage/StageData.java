package cz.jr.trailtour.backend.repository.entities.stage;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Jiří Rýdel on 5/5/20, 5:31 PM
 */
public class StageData extends Stage {

    @JsonProperty(value = "stravaData")
    private String stravaData;

    public String getStravaData() {
        return stravaData;
    }

    public void setStravaData(String stravaData) {
        this.stravaData = stravaData;
    }
}
