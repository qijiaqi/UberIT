package app.main.view;

import app.main.model.Carte;
import app.main.model.Delivery;
import app.main.model.Intersection;
import app.main.model.Request;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

public class IntersectionButton extends JButton {

    private Intersection intersection;
    private Carte carte;
    private Request request;
    private Delivery delivery;

    /**
     * Constructor of IntersectionButton
     * @param Carte carte
     * @param Intersection intersection
     * @param Color color
     */
    public IntersectionButton(Carte carte, Intersection intersection, Color color) {
        super();
        this.setBackground(color);
        this.intersection = intersection;
        this.carte = carte;
    }

    /**
     * @return intersection
     */
    public Intersection getIntersection() {
        return intersection;
    }

    /**
     * Set
     * @param Request request
     */
    public void setRequest(Request request) {
        this.request = request;
    }

    /**
     * @return Request
     */
    public Request getRequest() {
        return this.request;
    }

    
    /**
     * @param Delivery delivery
     */
    public Delivery getDelivery() {
        return this.delivery;
    }
    /**
     * @param Delivery delivery
     */
    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    /**
     * Create a String of the information the button should display
     * regarding if it is storing a request or a delivery
     * @return String
     */
    public String getInformation() {
        String res = "";
        if (request != null) {
            Set<String> streets = this.carte.getStreetsIntersection(intersection.getId());
            for (String street : streets) {
                res += street + "\n";
            }
            res += request.getTimeWindow() + "h - " + (request.getTimeWindow() + 1) + "h";
        }
        if (delivery != null) {
            Set<String> streets = this.carte.getStreetsIntersection(intersection.getId());
            for (String street : streets) {
                res += street + "\n";
            }
            res += "Arrivée à : " + delivery.getArrivedTime().getHours()+"h"+
                    (delivery.getArrivedTime().getMinutes() < 10 ?  "0"+ delivery.getArrivedTime().getMinutes() :delivery.getArrivedTime().getMinutes() ) +"\n";
            res += "Départ à : " + delivery.getLeftTime().getHours()+"h"+
            (delivery.getLeftTime().getMinutes() < 10 ?  "0"+ delivery.getLeftTime().getMinutes() :delivery.getLeftTime().getMinutes() );

        }

        return res;
    }

}
