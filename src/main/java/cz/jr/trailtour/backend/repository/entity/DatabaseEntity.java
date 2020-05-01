package cz.jr.trailtour.backend.repository.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Jiří Rýdel on 4/19/20, 1:22 PM
 */
public class DatabaseEntity {

    @JsonProperty(value = "id")
    private Long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
