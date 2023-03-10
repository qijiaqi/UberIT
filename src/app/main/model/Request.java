package app.main.model;

public class Request {
    private long intersectionId;
    private long timeWindow;
    private long courierId;

    public Request(long intersectionId, long timeWindow, long courierId) {
        this.intersectionId = intersectionId;
        this.timeWindow = timeWindow;
        this.courierId = courierId;
    }

    public Request() {

    }


    public long getIntersectionId() {
        return intersectionId;
    }

    public void setIntersectionId(long intersectionId) {
        this.intersectionId = intersectionId;
    }

    public long getTimeWindow() {
        return timeWindow;
    }

    public void setTimeWindow(long timeWindow) {
        this.timeWindow = timeWindow;
    }

    public long getCourierId() {
        return courierId;
    }

    public void setCourierId(long courierId) {
        this.courierId = courierId;
    }

    @Override
    public String toString() {
        return "Request{" +
                "intersectionId=" + intersectionId +
                ", timeWindow=" + timeWindow +
                ", courierId=" + courierId +
                '}';
    }
}
