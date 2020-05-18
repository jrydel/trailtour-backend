package cz.jr.trailtour.backend.repository.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import cz.jr.trailtour.backend.config.Constants;

import java.time.LocalDateTime;

/**
 * Created by Jiří Rýdel on 5/18/20, 11:27 PM
 */
public class Activity {

    @JsonProperty(value = "id")
    private long id;
    @JsonProperty(value = "position")
    private int position;
    @JsonProperty(value = "time")
    private int time;
    @JsonProperty(value = "created")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_FORMAT)
    private LocalDateTime created;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
}
