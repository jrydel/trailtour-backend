package cz.jr.trailtour.backend.repository.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Jiří Rýdel on 4/29/20, 3:06 PM
 */
public class Athlete extends DatabaseEntity {

    public enum Gender {
        M,
        F
    }

    @JsonProperty(value = "clubId")
    private Long clubId;
    @JsonProperty(value = "name")
    private String name;
    @JsonProperty(value = "gender")
    private Gender gender;

    public Long getClubId() {
        return clubId;
    }

    public void setClubId(Long clubId) {
        this.clubId = clubId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
