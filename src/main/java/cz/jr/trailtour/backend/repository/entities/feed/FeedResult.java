package cz.jr.trailtour.backend.repository.entities.feed;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.jr.trailtour.backend.repository.entities.Activity;
import cz.jr.trailtour.backend.repository.entities.Athlete;
import cz.jr.trailtour.backend.repository.entities.stage.Stage;

/**
 * Created by Jiří Rýdel on 5/2/20, 1:44 PM
 */
public class FeedResult {

    @JsonProperty(value = "athlete")
    private Athlete athlete;
    @JsonProperty(value = "stage")
    private Stage stage;
    @JsonProperty(value = "activity")
    private Activity activity;

    public Athlete getAthlete() {
        return athlete;
    }

    public void setAthlete(Athlete athlete) {
        this.athlete = athlete;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
