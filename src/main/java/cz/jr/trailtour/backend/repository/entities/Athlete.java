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
    @JsonProperty(value = "points")
    private Double points;
    @JsonProperty(value = "pointsTrailtour")
    private Double pointsTrailtour;
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

    public Double getPoints() {
        return points;
    }

    public void setPoints(Double points) {
        this.points = points;
    }

    public Double getPointsTrailtour() {
        return pointsTrailtour;
    }

    public void setPointsTrailtour(Double pointsTrailtour) {
        this.pointsTrailtour = pointsTrailtour;
    }

    public boolean isAbuser() {
        return abuser;
    }

    public void setAbuser(boolean abuser) {
        this.abuser = abuser;
    }
}
