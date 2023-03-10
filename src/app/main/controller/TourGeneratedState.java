package app.main.controller;

import app.main.model.Carte;
import app.main.model.Courier;
import app.main.view.IntersectionButton;
import app.main.view.LeftBar;
import app.main.view.MapView;
import app.main.view.Window;
import app.main.xml.FileChooser;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class TourGeneratedState implements State {

    public Map<String, Boolean> availableButtons;

    public TourGeneratedState() {
        this.availableButtons = new HashMap<>();
        this.availableButtons.put(LeftBar.CHARGER_CARTE, true);
        this.availableButtons.put(LeftBar.CHARGER_REQUETE, true);
        this.availableButtons.put(LeftBar.ADD_REQUEST, true);
        this.availableButtons.put(LeftBar.GENERER_TOUR, true);
        this.availableButtons.put(LeftBar.SAUVEGARDER_TOUR, true);
        this.availableButtons.put(LeftBar.SAUVEGARDER_REQUETE, true);
        this.availableButtons.put(LeftBar.ADD_COURIER, true);
        this.availableButtons.put(LeftBar.DELETE_COURIER, true);
        this.availableButtons.put(LeftBar.UNDO, true);
        this.availableButtons.put(LeftBar.REDO, true);
    }

    @Override
    public void majButtons(Window w) {
        w.leftBar.refreshButtons(availableButtons);
    }

    @Override
    public void loadMap(Controller controller, Carte carte, ListOfCommands l) throws Exception {
        File file = FileChooser.selectFile();
        if (file != null) {
            controller.setListOfCmds(new ListOfCommands());
            carte.loadMap(file);
            controller.window.eraseRequests();
            controller.setCurrentState(controller.LOADED_MAP_STATE);
        }
    }

    @Override
    public void loadRequest(Controller controller, Carte carte) throws Exception {
        File file = FileChooser.selectFile();
        carte.loadRequest(file);
        loadTour(controller, carte);
        controller.setCurrentState(controller.TOUR_GENERATED_STATE);
    }

    @Override
    public void loadTour(Controller controller, Carte carte) throws Exception {
        for (Map.Entry<Long, Courier> courierKV : carte.getCourierMap().entrySet()) {
            Courier courier = courierKV.getValue();
            courier.loadTour(carte);
            carte.addCourier(courier.getId(), courier);
            

            controller.window.mapView.repaint();
            boolean possibleTour = courier.loadTour(carte);
            if (!possibleTour)
            {            
                controller.window.mapView.setDeliveryOnButtons(courier.getTour());
                MapView.impossibleTourPopUp.setVisible(true);
                continue;
            }
            carte.addCourier(courier.getId(), courier);
        }
        controller.setCurrentState(controller.TOUR_GENERATED_STATE);
        controller.window.eraseRequests();
        controller.window.drawRequests();
    }

    @Override
    public void displayInformation(Controller controller, Carte carte, IntersectionButton intersectionButton) {
        controller.window.mapView.displayInformation(intersectionButton);
        controller.INFO_DISPLAYED_STATE.setAvailableButtons(this.availableButtons);
        controller.INFO_DISPLAYED_STATE.delivery = intersectionButton.getDelivery();
        controller.window.refreshWindow();
        controller.INFO_DISPLAYED_STATE.previousState = controller.getCurrentState();
        controller.setCurrentState(controller.INFO_DISPLAYED_STATE);
    }

    @Override
    public void addRequest(Controller controller, Carte carte) throws Exception {
        controller.setCurrentState(controller.ADD_REQUEST_STATE1);
    }

    @Override
    public void addCourier(Controller controller, Window w, Carte carte, ListOfCommands l) throws Exception {
        controller.ADD_COURIER_STATE.entryAction(carte, l, w);
        controller.setCurrentState(controller.ADD_COURIER_STATE);
    }

    @Override
    public void deleteCourier(Controller controller, Window w) {
        controller.DELETE_COURIER_STATE.entryAction(w);
        controller.setCurrentState(controller.DELETE_COURIER_STATE);
    }

    @Override
    public void displayCourier(Controller controller, Long courierId) {
        controller.window.topBar.displayCourier(courierId);
        controller.window.eraseRequests();
        controller.window.drawRequests();
    }

    @Override
    public void undo(ListOfCommands listOfCdes) {
        listOfCdes.undo();
    }

    @Override
    public void redo(ListOfCommands listOfCdes) {
        listOfCdes.redo();
    }
}
