package app.main.controller;

import java.util.HashMap;
import java.util.Map;

import app.main.model.Carte;
import app.main.model.Courier;
import app.main.view.LeftBar;
import app.main.view.TopBar;
import app.main.view.Window;

public class addCourierState implements State {

    public Map<String, Boolean> availableButtons;
    private Courier courier;

    public addCourierState() {
        this.availableButtons = new HashMap<>();
        this.availableButtons.put(LeftBar.CHARGER_CARTE, false);
        this.availableButtons.put(LeftBar.CHARGER_REQUETE, false);
        this.availableButtons.put(LeftBar.ADD_REQUEST, false);
        this.availableButtons.put(LeftBar.GENERER_TOUR, false);
        this.availableButtons.put(LeftBar.SAUVEGARDER_TOUR, false);
        this.availableButtons.put(LeftBar.SAUVEGARDER_REQUETE, false);
        this.availableButtons.put(LeftBar.DELETE_COURIER, false);

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
    public void entryAction(Carte carte, ListOfCommands l, Window window) {
        long newId = Courier.countCourier + 1;
        courier = new Courier(newId);
        window.mapView.afficherFormCourier(newId);
    }

    @Override
    public void validerCourier(Controller controller, Window window, Carte carte,ListOfCommands l) {

        // l.add(new AddCourierCommand(controller, carte, courier));	// Add request to Courier's requestList and update its tour and value in map
		controller.window.mapView.fermerFormCourier();
        carte.addCourier(courier);
        carte.notifyObservers();
        controller.window.addCourier(courier);

        if (carte.getRequestList().isEmpty()) {
            controller.setCurrentState(controller.LOADED_MAP_STATE);

        } else if (!carte.hasTours()){
            controller.setCurrentState(controller.REQUESTS_LOADED_STATE);
        } else {
            controller.setCurrentState(controller.TOUR_GENERATED_STATE);
        }

    }

}
