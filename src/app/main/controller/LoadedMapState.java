package app.main.controller;

import app.main.model.Carte;
import app.main.model.Courier;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import app.main.view.LeftBar;

import app.main.model.Intersection;
import app.main.view.Window;
import app.main.xml.FileChooser;

public class LoadedMapState implements State {

    public Map<String, Boolean> availableButtons;

    public LoadedMapState() {
        this.availableButtons = new HashMap<>();
        this.availableButtons.put(LeftBar.CHARGER_CARTE, true);
        this.availableButtons.put(LeftBar.CHARGER_REQUETE, true);
        this.availableButtons.put(LeftBar.ADD_REQUEST, true);
        this.availableButtons.put(LeftBar.GENERER_TOUR, false);
        this.availableButtons.put(LeftBar.SAUVEGARDER_TOUR, false);
        this.availableButtons.put(LeftBar.SAUVEGARDER_REQUETE, true);
        this.availableButtons.put(LeftBar.ADD_COURIER, true);
        this.availableButtons.put(LeftBar.DELETE_COURIER, true);
        this.availableButtons.put(LeftBar.UNDO, true);
        this.availableButtons.put(LeftBar.REDO, true);
    }

    @Override
    public Map<String, Boolean> getAvailableButtons() {
        return availableButtons;
    }

    @Override
    public void majButtons(Window w) {
        w.leftBar.refreshButtons(availableButtons);
    }

    @Override
    public void loadMap(Controller controller, Carte carte, ListOfCommands l) throws Exception {
        File file = FileChooser.selectFile();
        if(file != null)
        {
            controller.setListOfCmds(new ListOfCommands());
            carte.loadMap(file);
            controller.window.eraseRequests();
            controller.setCurrentState(controller.LOADED_MAP_STATE);
        }
    }
    
    @Override
    public void selectionnerIntersection(Controller controller, Window window, Carte carte,
            ListOfCommands listOfCommands, Intersection i) {
        controller.setCurrentState(controller.LOADED_MAP_STATE);
    }

    @Override
    public void loadRequest(Controller controller, Carte carte) throws Exception {
        File file = FileChooser.selectFile();
        carte.loadRequest(file);
        controller.setCurrentState(controller.REQUESTS_LOADED_STATE);
        controller.window.drawRequests();
    }

    @Override
    public void addRequest(Controller controller, Carte carte) throws Exception {
        controller.setCurrentState(controller.ADD_REQUEST_STATE1);
    }

//    @Override
//    public void loadTour(Controller controller, Carte carte) throws Exception {
//
//
//        for (Map.Entry<Long,Courier> courierKV : carte.getCourierMap().entrySet()) {
//            Courier courier = courierKV.getValue();
//            if(controller.window.topBar.displayedCouriers.get(courier.getId())) {
//            courier.loadTour(carte);
//            carte.addCourier(courier.getId(),courier);
//            }
//        }
//        controller.window.mapView.repaint();
//
//        controller.setCurrentState(controller.TOUR_GENERATED_STATE);
//    }
    @Override
    public void loadTour(Controller controller, Carte carte) throws Exception {

        for (Map.Entry<Long, Courier> courierKV : carte.getCourierMap().entrySet()) {
            Courier courier = courierKV.getValue();
            if (controller.window.topBar.displayedCouriers.get(courier.getId())) {
                courier.loadTour(carte);
                carte.addCourier(courier.getId(), courier);
            }
        }
        controller.window.mapView.repaint();

        controller.setCurrentState(controller.TOUR_GENERATED_STATE);
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
}