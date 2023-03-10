package app.main.controller;

import app.main.model.Carte;
import app.main.model.Courier;
import app.main.model.Delivery;
import app.main.model.Intersection;
import app.main.model.Request;
import app.main.view.IntersectionButton;
import app.main.view.LeftBar;
import app.main.view.Window;

import java.util.HashMap;
import java.util.Map;

/**
 * Class ModifRequestState
 */
public class ModifRequestState implements State {

    private Request request;
    public Map<String, Boolean> availableButtons;
    private Delivery delivery;
    /**
     * Constructeur ModifRequestState
     */
    public ModifRequestState() {
        this.availableButtons = new HashMap<>();
        this.availableButtons.put(LeftBar.CHARGER_CARTE, false);
        this.availableButtons.put(LeftBar.CHARGER_REQUETE, false);
        this.availableButtons.put(LeftBar.ADD_REQUEST, false);
        this.availableButtons.put(LeftBar.GENERER_TOUR, false);
        this.availableButtons.put(LeftBar.SAUVEGARDER_TOUR, false);
        this.availableButtons.put(LeftBar.SAUVEGARDER_REQUETE, false);
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
     * @param request
     * @param carte
     * @param window
     */
    public void entryAction(Request request, Carte carte, Window window) {
        this.request = request;
        window.mapView.afficherFormRequest();
    }

    /**
     * @param controller
     * @param window
     * @param carte
     * @param l
     */
    public void validerRequest(Controller controller, Window window, Carte carte, ListOfCommands l) {

        Courier courier;
        if (carte.hasTours())
        {
            this.request = carte.getRequestByIntersectionId(delivery.getIntersectionId());
            courier = carte.getCourierById(request.getCourierId());
        }
        else
        {
            courier = carte.getCourierById(request.getCourierId());        
        }
        l.add(new ModifyRequestCommand(controller, carte, courier,request,delivery));

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

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    
}
