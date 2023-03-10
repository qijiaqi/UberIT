package app.main.controller;

import app.main.model.Carte;
import app.main.model.Courier;
import app.main.model.Intersection;
import app.main.view.IntersectionButton;
import app.main.view.Window;

import java.util.Map;

public interface State {

    Map<String, Boolean> availableButtons = null;

    default void majButtons(Window w) {
    }

    default Map<String, Boolean> getAvailableButtons() {
        return availableButtons;
    }

    default void loadMap(Controller controller, Carte carte, ListOfCommands l) throws Exception {
    }


    default void loadRequest(Controller controller, Carte carte) throws Exception {
    }

    default void addRequest(Controller controller, Carte carte) throws Exception {
    }

    default void generateTour(Controller controller) {
    }

    default void saveTour(Controller controller) {
    }

    default void validerRequest(Controller controller, Window window, Carte carte, ListOfCommands listOfCdes) {
    };

    default void getChampRequest(Controller controller, Window window) {
    };

    default void selectionnerIntersection(Controller controller, Window window, Carte map,
            ListOfCommands listOfCommands, Intersection i) {
    }

    default void leftClick(Controller c, Window w, Carte carte, ListOfCommands l, Intersection intersection) {
    };

    default void loadTour(Controller controller, Carte carte) throws Exception {
    };

    default void displayInformation(Controller controller, Carte carte, IntersectionButton intersectionButton) {
    }

    default void addCourier(Controller controller, Window w, Carte carte, ListOfCommands l) throws Exception {
    };

    default void deleteRequest(Controller controller, Carte carte, ListOfCommands l) {
    }

    default void validerCourier(Controller controller, Window window, Carte carte,ListOfCommands l) {
    }

    default void entryAction(Carte carte, ListOfCommands l, Window window) {
    }

    default void entryAction(Intersection intersection, Carte carte, ListOfCommands l, Window window) {
    }

    default void modifRequest(Controller controller, Window window, Carte carte) {
    }

    default void displayCourier(Controller controller, Long courierId) {
    }

    default void undo(ListOfCommands listOfCdes) {
    }

    default void redo(ListOfCommands listOfCdes) {
    }

    default void entryAction(Window window) {
    }

    default void deleteCourier(Controller controller, Window w) {
    }

    default void validerDelCourier(Controller controller, Window window, Carte carte,ListOfCommands l) {
    }

    default void removeInformation(Controller controller){};

    default void annulerAction(Controller controller, Window window, Carte carte){};
}
