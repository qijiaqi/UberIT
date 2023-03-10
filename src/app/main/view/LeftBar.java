package app.main.view;

import app.main.controller.Controller;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.*;

/**
 * Component of the window where we can find the buttons
 */
public class LeftBar extends JPanel {

    protected final static Dimension LEFTBAR_DIMENSION = new Dimension(193,692);

    public final static String CHARGER_CARTE = "Charger une carte";
    public final static String ADD_REQUEST = "Ajouter une requête";
    public final static String CHARGER_REQUETE = "Charger des requêtes";
    public final static String SAUVEGARDER_REQUETE = "Sauvegarder requêtes";
    public final static String GENERER_TOUR = "Générer les tours";
    public final static String SAUVEGARDER_TOUR = "Sauvegarder le tour";
    public final static String ADD_COURIER = "Ajouter un livreur";
    public final static String DELETE_COURIER = "Supprimer un livreur";
    public final static String UNDO = "Undo";
    public final static String REDO = "Redo";

    /**
     * Array of strings where all the buttons'text are in
     */
    private final static String[] buttonsText = new String[] { CHARGER_CARTE, CHARGER_REQUETE, ADD_REQUEST,
            GENERER_TOUR, SAUVEGARDER_REQUETE, ADD_COURIER, DELETE_COURIER, UNDO, REDO };
    /**
     * Map storing all the buttons with their text
     */
    private Map<String, JButton> buttons;

    /**
     * Button Listener
     */
    private ButtonListener buttonListener;
    private Controller controller;


    public LeftBar(Controller controller) {
        super();
        this.controller = controller;
        this.setLayout(new FlowLayout());
        this.setMaximumSize(LEFTBAR_DIMENSION);
        this.setMinimumSize(LEFTBAR_DIMENSION);
        this.setPreferredSize(LEFTBAR_DIMENSION);
        this.createButtons();
    }

    private void createButtons() {
        JPanel panelButton;
        JButton button;
        this.buttons = new HashMap<>();
        this.buttonListener = new ButtonListener(this.controller);
        for (String text : buttonsText) {
            panelButton = new JPanel();
            button = new ActionButton(text);
            button.setBorder(new EmptyBorder(56, 0, 56, 0));
            panelButton.setBorder(new EmptyBorder(0, 0, 0, 0));
            button.addActionListener(this.buttonListener);
            button.setEnabled(false);
            this.buttons.put(text, button);
            panelButton.add(button);
            this.add(panelButton);
        }
    }

    public void refreshButtons(Map<String, Boolean> availableBoutons) {
        for (String textButton : availableBoutons.keySet()) {
            if (buttons.get(textButton) != null) {
                buttons.get(textButton).setEnabled(availableBoutons.get(textButton));
            }
        }
    }

}
