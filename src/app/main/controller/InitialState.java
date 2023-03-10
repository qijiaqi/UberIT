package app.main.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import app.main.view.LeftBar;
import org.xml.sax.SAXException;

import app.main.model.Carte;
import app.main.model.Intersection;
import app.main.view.Window;
import app.main.xml.FileChooser;
import app.main.xml.XMLdeserializer;

public class InitialState implements State {
    // Etat initial

    public Map<String, Boolean> availableButtons;

    public InitialState() {
        this.availableButtons = new HashMap<>();
        availableButtons.put(LeftBar.CHARGER_CARTE, true);
        availableButtons.put(LeftBar.CHARGER_REQUETE, false);
        availableButtons.put(LeftBar.ADD_REQUEST, false);
        availableButtons.put(LeftBar.GENERER_TOUR, false);
        availableButtons.put(LeftBar.SAUVEGARDER_TOUR, false);
        this.availableButtons.put(LeftBar.SAUVEGARDER_REQUETE, false);
        this.availableButtons.put(LeftBar.ADD_COURIER, false);

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
            l = new ListOfCommands();
            carte.loadMap(file);
            controller.window.eraseRequests();       
            controller.setCurrentState(controller.LOADED_MAP_STATE);
        }
    }

    @Override
    public Map<String, Boolean> getAvailableButtons() {
        return availableButtons;
    }
}
