package app.main.controller;

import app.main.model.Carte;
import app.main.model.Courier;
import app.main.model.Delivery;
import app.main.model.Request;
import app.main.view.IntersectionButton;
import app.main.view.Window;
import app.main.xml.FileChooser;

import java.io.File;
import java.util.Map;

public class InfoDisplayedState implements State{

    public Map<String,Boolean> availableButtons;
    public Request request;
    public State previousState;
    public Delivery delivery;
    public InfoDisplayedState(){}

    public void setAvailableButtons(Map<String,Boolean> availableButtons){
        this.availableButtons = availableButtons;
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
    public void loadRequest(Controller controller, Carte carte) throws Exception {
        File file = FileChooser.selectFile();
        carte.loadRequest(file);
        controller.window.mapView.removeInformation();
        controller.window.refreshWindow();
        controller.setCurrentState(controller.REQUESTS_LOADED_STATE);
    }

    @Override
    public void addRequest(Controller controller, Carte carte) throws Exception {
        controller.window.mapView.removeInformation();
        controller.window.refreshWindow();
        controller.setCurrentState(controller.ADD_REQUEST_STATE1);
    }


    @Override
    public void displayInformation(Controller controller,Carte carte, IntersectionButton intersectionButton) {
        controller.window.mapView.removeInformation();
        controller.window.refreshWindow();
        if (carte.hasTours())
        {
            controller.setCurrentState(controller.TOUR_GENERATED_STATE);
        }
        else
        {
            controller.setCurrentState(controller.REQUESTS_LOADED_STATE);
        }
    }

    public void removeInformation(Controller controller){
        controller.window.mapView.removeInformation();
        controller.window.refreshWindow();
        controller.setCurrentState(controller.REQUESTS_LOADED_STATE);
    }

    public void deleteRequest(Controller controller, Carte carte,ListOfCommands l){
        
        //1 - recupérer le courier associé à la requete
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
        // System.out.println("courier en charge de la requete :"+courier);
        // //2 - suppr la requete de la requeteList du courier
        // courier.deleteRequestCourier(request);
        // System.out.println("requete du livreur : "+courier.getRequestList());
        // //3 - réinserer le courier dans la map<Long,Courier> de la Carte
        // carte.addCourier(courier.getId(), courier);
        // //4 - suppr la requete de la requestListe de Carte
        // carte.deleteRequest(request);
        // System.out.println("requete de la carte : "+carte.getRequestList());
        l.add(new ReverseCommand(new AddRequestCommand(controller,carte, request, courier)));

        // controller.window.mapView.removeInformation();
        // IntersectionButton button = controller.window.mapView.getButtonByRequest(request);
        // controller.window.mapView.remove(button);
        // controller.window.refreshWindow();
        //controller.setCurrentState(controller.REQUESTS_LOADED_STATE);
    }

    public void modifRequest(Controller controller, Window window, Carte carte){
        controller.MODIF_REQUEST_STATE.entryAction(request, carte, window);
        controller.MODIF_REQUEST_STATE.setDelivery(delivery);
        controller.setCurrentState(controller.MODIF_REQUEST_STATE);
    }

}
