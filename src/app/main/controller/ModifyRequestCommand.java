package app.main.controller;

import app.main.algo.GenerateTour;
import app.main.model.Carte;
import app.main.model.Courier;
import app.main.model.Delivery;
import app.main.model.Request;
import app.main.model.Tour;
import app.main.view.IntersectionButton;
import app.main.view.MapView;

public class ModifyRequestCommand implements Command {

    private Carte carte;
    private Courier oldCourier;
    private Courier courier;
    private Controller controller;
    private Request oldRequest;
    private Request request;
    private Tour oldTour1;
    private Tour oldTour2;
    private Delivery delivery;
    private Delivery newDelivery;

    /**
     * Create the command which adds the request s to the carte p
     * 
     * @param p the carte to which f is added
     * @param s the request added to p
     */
    public ModifyRequestCommand(Controller controller, Carte c, Courier co, Request r, Delivery delivery) {
        this.carte = c;
        this.oldCourier = co;
        this.oldRequest = r;
        this.controller = controller;
        this.delivery = delivery;
    }

    @Override
    public void doCommand() {

        oldCourier.deleteRequestCourier(oldRequest);
        carte.deleteRequest(oldRequest);

        // ajoute une nouvelle requete à la place
        String champHeure = controller.window.mapView.getChampHeure();
        long idLivreur = controller.window.mapView.getLivreurSelected();
        long heure;
        IntersectionButton button;
        heure = Long.parseLong(champHeure);
        request = new Request();
        request.setCourierId(idLivreur);
        request.setTimeWindow(heure);
        request.setIntersectionId(oldRequest.getIntersectionId());

        courier = carte.getCourierById(idLivreur);
        courier.addRequestCourier(request);
        if (carte.hasTours()) {
            button = controller.window.mapView.getButtonByDelivery(delivery);
            oldTour1 = oldCourier.getTour();
            oldTour2 = courier.getTour();
            GenerateTour courierTourGenerator = new GenerateTour(courier, carte);
            GenerateTour oldCourierTourGenerator = new GenerateTour(oldCourier, carte);

            if (courierTourGenerator.isTourPossible() && oldCourierTourGenerator.isTourPossible()) {
                oldCourier.setTour(oldCourierTourGenerator.getTour());
                courier.setTour(courierTourGenerator.getTour());
                carte.addCourier(oldCourier.getId(), oldCourier);
                carte.addCourier(courier.getId(), courier);
                carte.addRequest(request);
                newDelivery = courier.getTour().getDeliveryByIntersectionId(request.getIntersectionId());
                carte.notifyObservers();
            } else {
                courier.deleteRequestCourier(request);
                oldCourier.addRequestCourier(oldRequest);
                carte.addCourier(oldCourier.getId(), oldCourier);
                courier.deleteRequestCourier(request);
                carte.addCourier(oldCourier.getId(), oldCourier);
                MapView.impossibleTourPopUp.setVisible(true);
            }
            controller.setCurrentState(controller.TOUR_GENERATED_STATE);
        } else {
            button = controller.window.mapView.getButtonByRequest(oldRequest);
            carte.addCourier(courier.getId(), courier);
            carte.addRequest(request);
            carte.notifyObservers();
            controller.setCurrentState(controller.REQUESTS_LOADED_STATE);
        }
        controller.window.mapView.remove(button);
        controller.window.mapView.fermerFormRequest();

        controller.window.mapView.removeInformation();
        controller.window.eraseRequests();
        controller.window.drawRequests();

    }

    @Override
    public void undoCommand() {

        IntersectionButton button;
        courier.deleteRequestCourier(request);
        carte.deleteRequest(request);
        oldCourier.addRequestCourier(oldRequest);
        if (carte.hasTours()) {
            button = controller.window.mapView.getButtonByDelivery(newDelivery);
            oldCourier.setTour(oldTour1);
            courier.setTour(oldTour2);
            controller.setCurrentState(controller.TOUR_GENERATED_STATE);
        } else {
            button = controller.window.mapView.getButtonByRequest(request);
            controller.setCurrentState(controller.REQUESTS_LOADED_STATE);
        }
        carte.addCourier(courier.getId(), courier);
        carte.addCourier(oldCourier.getId(), oldCourier);


        controller.window.mapView.remove(button);

        carte.addRequest(oldRequest);

        controller.window.mapView.removeInformation();
        controller.window.eraseRequests();
        controller.window.drawRequests();

    }

}
