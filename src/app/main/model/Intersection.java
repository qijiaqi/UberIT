package app.main.model;

import java.util.List;

public class Intersection {
    private long id;
    private double latitude;
    private double longitude;

    public Intersection(long id, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Intersection(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Intersection(Intersection intersection) {
        this.id = intersection.getId();
        this.latitude = intersection.getLatitude();
        this.longitude = intersection.getLongitude();
    }

    public Intersection() {

    }

    public long getId() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Intersection{" +
                "id=" + id +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Intersection)) return false;
        if (!super.equals(object)) return false;
        Intersection that = (Intersection) object;
        return java.lang.Double.compare(that.getLatitude(), getLatitude()) == 0 && java.lang.Double.compare(that.getLongitude(), getLongitude()) == 0;
    }

    public int hashCode() {
        return java.util.Objects.hash(super.hashCode(), getLatitude(), getLongitude());
    }

    public Intersection getIntersectionById(long id, List<Intersection> intersectionList) {
        for (Intersection intersection : intersectionList ) {
            if(intersection.getId() == id){
                return intersection;
            }
        }
        return null;
    }

}


