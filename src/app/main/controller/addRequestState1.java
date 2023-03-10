package app.main.controller;

import app.main.model.Carte;
import app.main.model.Intersection;
import app.main.view.LeftBar;
import app.main.view.Window;

import java.util.HashMap;
import java.util.Map;

/**
 * Class addRequestState1
 */
public class addRequestState1 implements State {

    public Map<String, Boolean> availableButtons;

    /**
     * Constructor addRequestState1
     */
    public addRequestState1() {
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
     * @param c
     * @param w
     * @param carte
     * @param l
     * @param intersection
     */
    @Override
    public void selectionnerIntersection(Controller c, Window w, Carte carte, ListOfCommands l,
            Intersection intersection) {
        // A FAIRE : check si on peut sortir de l'intersection
        if (carte.getIntersectionById(intersection.getId()) != null) {
            c.ADD_REQUEST_STATE2.entryAction(intersection, carte, l, w);
            c.setCurrentState(c.ADD_REQUEST_STATE2);
        } else {
        }
    }
}
