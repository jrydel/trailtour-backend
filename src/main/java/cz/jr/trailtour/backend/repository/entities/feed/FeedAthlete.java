package cz.jr.trailtour.backend.repository.entities.feed;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Jiří Rýdel on 5/5/20, 5:58 PM
 */
public class FeedAthlete {

    @JsonProperty(value = "id")
    private long id;
    @JsonProperty(value = "name")
    private String name;
    @JsonProperty(value = "club")
    private String club;
    @JsonProperty(value = "abuser")
    private boolean abuser;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public boolean isAbuser() {
        return abuser;
    }

    public void setAbuser(boolean abuser) {
        this.abuser = abuser;
    }
}
