package app.main.model;

import java.util.Objects;

public class Segment {
    private long idOrigin;
    private long IdDestination;
    private String street;
    private double length;

    public Segment(long idOrigin, long idDestination, String street, double length) {
        this.idOrigin = idOrigin;
        IdDestination = idDestination;
        this.street = street;
        this.length = length;
    }

    public Segment() {
    }

    public long getIdOrigin() {
        return idOrigin;
    }

    public void setIdOrigin(long idOrigin) {
        this.idOrigin = idOrigin;
    }

    public long getIdDestination() {
        return IdDestination;
    }

    public void setIdDestination(long idDestination) {
        IdDestination = idDestination;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "Segment{" +
                "idOrigin=" + idOrigin +
                ", IdDestination=" + IdDestination +
                ", street='" + street + '\'' +
                ", length=" + length +
                '}';
    }
}
