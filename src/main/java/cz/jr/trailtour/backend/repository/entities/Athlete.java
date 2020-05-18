package cz.jr.trailtour.backend.repository.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Jiří Rýdel on 4/29/20, 3:06 PM
 */
public class Athlete extends DatabaseEntity {

    @JsonProperty(value = "name")
    private String name;
    @JsonProperty(value = "gender")
    private String gender;
    @JsonProperty(value = "club")
    private String club;
    @JsonProperty(value = "abuser")
    private boolean abuser;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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
