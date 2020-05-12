package cz.jr.trailtour.backend.repository.entities.athlete;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Jiří Rýdel on 5/12/20, 4:05 PM
 */
public class AthleteStage {

    @JsonProperty(value = "number")
    private Integer number;
    @JsonProperty(value = "name")
    private String name;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
