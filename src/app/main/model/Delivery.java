package app.main.model;

import java.util.Date;

public class  Delivery {
    private Date arrivedTime;
    private Date leftTime;
    private long waitingTime;
    private long intersectionId;

    public Delivery(long intersectionId, Date arrivedTime, Date leftTime, long waitingTime) {
        this.arrivedTime = arrivedTime;
        this.leftTime = leftTime;
        this.waitingTime = waitingTime;
        this.intersectionId = intersectionId;
    }

    public Date getArrivedTime() {
        return arrivedTime;
    }

    public void setArrivedTime(Date arrivedTime) {
        this.arrivedTime = arrivedTime;
    }

    public Date getLeftTime() {
        return leftTime;
    }

    public void setLeftTime(Date leftTime) {
        this.leftTime = leftTime;
    }

    public long getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(long waitingTime) {
        this.waitingTime = waitingTime;
    }

    public long getIntersectionId() {
        return intersectionId;
    }

    public void setIntersectionId(long intersectionId) {
        this.intersectionId = intersectionId;
    }

    @Override
    public String toString() {
        return "Delivery{" +
                "arrivedTime=" + arrivedTime +
                ", leftTime=" + leftTime +
                ", waitingTime=" + waitingTime +
                ", intersectionId=" + intersectionId +
                '}';
    }
}
