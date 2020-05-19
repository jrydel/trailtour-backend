package cz.jr.trailtour.backend.repository.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Jiří Rýdel on 4/29/20, 3:02 PM
 */
public class Result {

    @JsonProperty(value = "athlete")
    private Athlete athlete;
    @JsonProperty(value = "activity")
    private Activity activity;
    @JsonProperty(value = "activityResult")
    private ActivityResult activityResult;

    public Athlete getAthlete() {
        return athlete;
    }

    public void setAthlete(Athlete athlete) {
        this.athlete = athlete;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public ActivityResult getActivityResult() {
        return activityResult;
    }

    public void setActivityResult(ActivityResult activityResult) {
        this.activityResult = activityResult;
    }
}
