package cz.jr.trailtour.backend.repository.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Jiří Rýdel on 4/21/20, 1:33 PM
 */
public class Club extends DatabaseEntity {

    @JsonProperty(value = "name")
    private final String name;

    public Club(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
