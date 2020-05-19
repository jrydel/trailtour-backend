package cz.jr.trailtour.backend.repository.entities.athlete;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.jr.trailtour.backend.repository.entities.Activity;
import cz.jr.trailtour.backend.repository.entities.ActivityResult;
import cz.jr.trailtour.backend.repository.entities.stage.Stage;

/**
 * Created by Jiří Rýdel on 5/11/20, 12:13 PM
 */
public class AthleteResult {

    @JsonProperty(value = "activityResult")
    private ActivityResult activityResult;
    @JsonProperty(value = "activity")
    private Activity activity;
    @JsonProperty(value = "stage")
    private Stage stage;

    public ActivityResult getActivityResult() {
        return activityResult;
    }

    public void setActivityResult(ActivityResult activityResult) {
        this.activityResult = activityResult;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
