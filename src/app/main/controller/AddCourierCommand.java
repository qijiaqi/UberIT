package app.main.controller;

import app.main.algo.GenerateTour;
import app.main.model.Carte;
import app.main.model.Courier;
import app.main.model.Request;
import app.main.view.IntersectionButton;
import app.main.view.MapView;

public class AddCourierCommand implements Command {
	
	private Carte carte;
	private Request request;
	private Courier courier;
	private Controller controller;

	/**
	 * Create the command which adds the request s to the carte p
	 * @param p the carte to which f is added
	 * @param s the request added to p
	 */
	public AddCourierCommand(Controller controller,Carte c,Courier co){
		this.carte = c;
		this.courier = co;
		this.controller = controller;
		}

	@Override
	public void doCommand() {
		// Add request to Courier's requestList and update its tour and value in map
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

	@Override
	public void undoCommand() {
		if (!carte.getCourierMap().isEmpty()) {
            carte.deleteCourier(courier.getId());
            carte.notifyObservers();
            controller.window.deleteCourier(courier.getId());
        } else {
            System.out.println("La map de livreur est vide");
        }

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
