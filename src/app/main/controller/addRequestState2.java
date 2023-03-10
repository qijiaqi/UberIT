package app.main.controller;

import app.main.model.Carte;
import app.main.model.Courier;
import app.main.model.Intersection;
import app.main.model.Request;
import app.main.view.LeftBar;
import app.main.view.Window;

import java.util.HashMap;
import java.util.Map;

/**
 * Class addRequestState2
 */
public class addRequestState2 implements State {

    private Request request;
    public Map<String, Boolean> availableButtons;

    /**
     * Constructeur addRequestState2
     */
    public addRequestState2() {
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

    /**
     * @param w
     */
    @Override
    public void majButtons(Window w) {
        w.leftBar.refreshButtons(availableButtons);
    }

    /**
     * @param intersection
     * @param carte
     * @param l
     * @param window
     */
    @Override
    public void entryAction(Intersection intersection, Carte carte, ListOfCommands l, Window window) {
        request = new Request();
        request.setIntersectionId(intersection.getId());
        window.mapView.afficherFormRequest();
    }

    /**
     * @param controller
     * @param window
     * @param carte
     * @param l
     */
    @Override
    public void validerRequest(Controller controller, Window window, Carte carte, ListOfCommands l) {
        String champHeure = window.mapView.getChampHeure();
        long idLivreur = window.mapView.getLivreurSelected();

        long heure;
        try {
            heure = Long.parseLong(champHeure);
            request.setCourierId(idLivreur);
            request.setTimeWindow(heure);

            Courier courier = carte.getCourierById(idLivreur);

            l.add(new AddRequestCommand(controller,carte, request, courier));
            // courier.addRequestCourier(request);
            // carte.addCourier(idLivreur, courier);
            // carte.addRequest(request);
        } catch (NumberFormatException e) {
            System.out.println("ERREUR");
            // Invalid double String.
        }
        window.mapView.fermerFormRequest();

        //controller.setCurrentState(controller.REQUESTS_LOADED_STATE);
    }

    /**
     * @param controller
     * @param window
     * @param carte
     */
    public void annulerAction(Controller controller, Window window, Carte carte){
        window.mapView.fermerFormRequest();
        if (carte.getRequestList().isEmpty()) {
            controller.setCurrentState(controller.LOADED_MAP_STATE);
        } else {
            controller.setCurrentState(controller.REQUESTS_LOADED_STATE);
        }
    }
}
