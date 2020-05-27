package cz.jr.trailtour.backend.repository.entities;

/**
 * Created by Jiří Rýdel on 5/27/20, 2:21 PM
 */
public class Coordinates {

    private double latitude;
    private double longitude;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
