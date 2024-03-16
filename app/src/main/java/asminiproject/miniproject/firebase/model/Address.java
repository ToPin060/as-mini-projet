package asminiproject.miniproject.firebase.model;

public class Address {
    private float lon;
    private float lat;

    public Address() { }

    public Address(float _lon, float _lat) {
        this.lon = _lon;
        this.lat = _lat;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

}
