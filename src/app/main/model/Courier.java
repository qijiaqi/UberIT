package app.main.model;

import app.main.algo.GenerateTour;
import app.main.observer.Observable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class Courier {
    private long id;
    private List<Request> requestList;
    private Tour tour;

    public static long countCourier = 0;

    /**
     * Surcharge constructeur Courier
     * @param oneId
     * @param requestList
     */
    public Courier(long oneId, List<Request> requestList) {
        this.id = oneId;
        this.requestList = requestList;
        countCourier++;
    }

    /**
     * Surcharge constructeur Courier
     * @param id
     */
    public Courier(long id) {
        this.id = id;
        this.requestList = new LinkedList<>();
        countCourier++;

    }

    /**
     * Constructeur par defaut Courier
     */
    public Courier() {
        countCourier++;
    }

    public long getId() {
        return id;
    }

    public Tour getTour() {
        return tour;
    }

    /**
     * @param id
     * @return
     */
    public long getTimeWindowById(long id) {
        int i = 0;
        while (i < requestList.size()) {
            if (requestList.get(i).getIntersectionId() == id) {
                break;
            }
            i++;
        }
        return requestList.get(i).getTimeWindow();
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public void addRequestCourier(Request request) {
        this.requestList.add(request);
    }

    public void deleteRequestCourier(Request request) {
        this.requestList.remove(request);
    }

    public List<Request> getRequestList() {
        return requestList;
    }

    public void setRequestList(List<Request> requestList) {
        this.requestList = requestList;
    }

    @Override
    public String toString() {
        return "Courier{" +
                "id=" + id + "}";
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean loadTour(Carte carte) throws Exception {

        GenerateTour tourGenerator = new GenerateTour(this, carte);
        if (tourGenerator.isTourPossible())
        {
            this.tour = tourGenerator.getTour();
            return true;
        }
        return false;
    }

    /**
     * reset requestList and tour for a courier
     */
    public void reset() {
        this.requestList = new LinkedList<>();
        this.tour = null;
    }
}