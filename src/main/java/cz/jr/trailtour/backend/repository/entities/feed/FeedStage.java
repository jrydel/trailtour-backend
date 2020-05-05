package cz.jr.trailtour.backend.repository.entities.feed;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Jiří Rýdel on 5/5/20, 5:56 PM
 */
public class FeedStage {

    @JsonProperty(value = "number")
    private int number;
    @JsonProperty(value = "name")
    private String name;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
