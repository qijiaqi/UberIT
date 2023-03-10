package app.main.controller;

import java.util.HashMap;
import java.util.Map;

import app.main.model.Carte;
import app.main.model.Courier;
import app.main.view.LeftBar;
import app.main.view.TopBar;
import app.main.view.Window;

public class deleteCourierState implements State {

    public Map<String, Boolean> availableButtons;

    public deleteCourierState() {
        this.availableButtons = new HashMap<>();
        this.availableButtons.put(LeftBar.CHARGER_CARTE, false);
        this.availableButtons.put(LeftBar.CHARGER_REQUETE, false);
        this.availableButtons.put(LeftBar.ADD_REQUEST, false);
        this.availableButtons.put(LeftBar.GENERER_TOUR, false);
        this.availableButtons.put(LeftBar.SAUVEGARDER_TOUR, false);
        this.availableButtons.put(LeftBar.SAUVEGARDER_REQUETE, false);
        this.availableButtons.put(LeftBar.ADD_COURIER, false);
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
    public void entryAction(Window window) {
        window.mapView.afficherDeleteCourier();
    }

    @Override
    public void validerDelCourier(Controller controller, Window window, Carte carte,ListOfCommands l) {
        long idToDelete = window.mapView.getDelCourier();
        Courier courier = carte.getCourierById(idToDelete);
        //l.add(new ReverseCommand(new AddCourierCommand(controller, carte, courier)));

        if (!carte.getCourierMap().isEmpty()) {
            carte.deleteCourier(courier.getId());
            carte.notifyObservers();
            controller.window.deleteCourier(courier.getId());
        } else {
            System.out.println("La map de livreur est vide");
        }
        controller.setListOfCmds(new ListOfCommands());
        if (carte.getRequestList().isEmpty()) {
            controller.setCurrentState(controller.LOADED_MAP_STATE);

        } else {
            controller.setCurrentState(controller.REQUESTS_LOADED_STATE);

        }
        controller.window.mapView.fermerDeleteCourier();
        controller.window.eraseRequests();
		controller.window.drawRequests();

        controller.window.refreshWindow();

    }

}
