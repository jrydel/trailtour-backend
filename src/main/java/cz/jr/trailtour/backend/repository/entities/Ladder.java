package cz.jr.trailtour.backend.repository.entities;

/**
 * Created by Jiří Rýdel on 5/26/20, 2:02 PM
 */
public class Ladder {

    private Integer position;
    private Double points;
    private Integer trailtourPosition;
    private Double trailtourPoints;

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Double getPoints() {
        return points;
    }

    public void setPoints(Double points) {
        this.points = points;
    }

    public Integer getTrailtourPosition() {
        return trailtourPosition;
    }

    public void setTrailtourPosition(Integer trailtourPosition) {
        this.trailtourPosition = trailtourPosition;
    }

    public Double getTrailtourPoints() {
        return trailtourPoints;
    }

    public void setTrailtourPoints(Double trailtourPoints) {
        this.trailtourPoints = trailtourPoints;
    }
}
