package app.main.view;

import app.main.controller.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.AbstractButton;

/**
 * ActionListener used for the button events
 */
public class ButtonListener implements ActionListener {

    private Controller controller;

    /**
     * Contrusctor of the ButtonListener
     * @param Controller c
     */
    public ButtonListener(Controller c) {
        this.controller = c;
    }

    /**
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        // Pour tous les boutons de la LeftBar
        switch (e.getActionCommand()) {
            case LeftBar.UNDO:
                controller.undo();
                break;
            case LeftBar.REDO:
                controller.redo();
                break;
            case LeftBar.CHARGER_CARTE:
                controller.selectionnerCarte();
                break;
            case LeftBar.CHARGER_REQUETE:
                controller.selectionnerRequete();
                break;
            case LeftBar.ADD_REQUEST:
                controller.addRequest();
                break;
            case LeftBar.GENERER_TOUR:
                controller.loadTour();
                break;
            case LeftBar.SAUVEGARDER_REQUETE:
                controller.saveRequest();
                break;
            case LeftBar.ADD_COURIER:
                controller.addCourier();
                break;
            case LeftBar.DELETE_COURIER:
                controller.deleteCourier();
                break;
        }

        if (e.getSource() == MapView.boutonValiderRequete) { // Pour valider la création/modification d'une requête
            controller.validerRequest();
        } else if (e.getSource() == MapView.modifButton) { // Pour modifier une requête
            controller.modifRequest();
        } else if (e.getSource() == MapView.suppButton) { // Pour supprimer une requête
            controller.deleteRequest();
        }else if (e.getSource() == MapView.boutonAnnuler) { // Pour annuler un ajout ou une modification de requete
            controller.annulerAction();
        }else if (e.getSource() == MapView.boutonValiderCourier) { // Pour valider l'ajout d'un livreur
            controller.validerCourier();
        } else if (e.getSource() == MapView.boutonValiderDeleteCourier) { // Pour valider la suppression d'un livreur
            controller.validerDelCourier();
        }
        if (MapView.intersectionButtons.contains(e.getSource())) { // S'il s'agite d'un bouton d'intersection (requête ou livraison)
            controller.displayInformation((IntersectionButton) e.getSource()); // Alors on affiche ses infos
        }
        if (e.getActionCommand().split(" ")[0].equals("Livreur")) { // S'il s'agit d'un bouton de livreur
            controller.displayCourier(Long.parseLong(e.getActionCommand().split(" ")[1])); // On récupère son id et on l'affiche (ou l'enlève)
        }
        if (e.getSource() == MapView.wareHousePopUpCloseButton) {
            MapView.wareHouseClickedPopUp.setVisible(false);
        }
        if (e.getSource() == MapView.impossibleTourPopUpCloseButton) {
            MapView.impossibleTourPopUp.setVisible(false);
        }
    }
}