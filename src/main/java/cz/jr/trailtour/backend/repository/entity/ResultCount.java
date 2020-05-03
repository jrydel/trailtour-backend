package cz.jr.trailtour.backend.repository.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Jiří Rýdel on 5/3/20, 11:38 PM
 */
public class ResultCount {

    @JsonProperty(value = "M")
    private int male;
    @JsonProperty(value = "F")
    private int female;
    @JsonProperty(value = "C")
    private int club;

    public int getMale() {
        return male;
    }

    public void setMale(int male) {
        this.male = male;
    }

    public int getFemale() {
        return female;
    }

    public void setFemale(int female) {
        this.female = female;
    }

    public int getClub() {
        return club;
    }

    public void setClub(int club) {
        this.club = club;
    }
}
