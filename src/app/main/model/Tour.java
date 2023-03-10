package app.main.model;

import java.util.List;

public class Tour {
    
    private long id;
    private List<Delivery> orderedDeliveries;
    private Courier courier;
    private List<Segment> path;

    public Tour(long id,List<Delivery> orderedDeliveries, List<Segment> path, Courier courier) {
        this.id = id;
        this.courier = courier;
        this.orderedDeliveries = orderedDeliveries;
        this.path = path;
    }

    public Tour(List<Delivery> orderedDeliveries,List<Segment> path, Courier courier) {
        this.courier = courier;
        this.orderedDeliveries = orderedDeliveries;
        this.path = path;
    }

    public Tour(long id,List<Segment> path, Courier courier) {
        this.id = id;
        this.courier = courier;
        this.path = path;
    }


    public List<Delivery> getOrderedDeliveries() {
        return orderedDeliveries;
    }

    public void setOrderedDeliveries(List<Delivery> orderedDeliveries) {
        this.orderedDeliveries = orderedDeliveries;
    }

    public long getId() {
        return id;
    }

    public Courier getCourier() {
        return courier;
    }

    public List<Segment> getPath() {
        return this.path;
    }

    public List<Segment> setPath(List<Segment> path) {
        return this.path = path;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    public void setId(long id) {
        this.id = id;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Tour{" +
                ", id=" + id +
                ", courier=" + courier +
                '}';
    }

    public Delivery getDeliveryByIntersectionId(long id){
        Delivery result;
        for (Delivery d : this.orderedDeliveries)
        {
            if (d.getIntersectionId() == id)
            {
                return d;
            }
        }
        return null;
    }
}
