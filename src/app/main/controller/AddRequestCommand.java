package app.main.controller;

import app.main.algo.GenerateTour;
import app.main.model.Carte;
import app.main.model.Courier;
import app.main.model.Request;
import app.main.view.IntersectionButton;
import app.main.view.MapView;

public class AddRequestCommand implements Command {
	
	private Carte carte;
	private Request request;
	private Courier courier;
	private Controller controller;

	/**
	 * Create the command which adds the request s to the carte p
	 * @param p the carte to which f is added
	 * @param s the request added to p
	 */
	public AddRequestCommand(Controller controller,Carte c, Request r,Courier co){
		this.carte = c;
		this.request = r;
		this.courier = co;
		this.controller = controller;
		}

	@Override
	public void doCommand() {
		// Add request to Courier's requestList and update its tour and value in map
		courier.addRequestCourier(request);
		if (carte.hasTours())
		{
			GenerateTour tourGenerator = new GenerateTour(courier,carte);
			
			if (tourGenerator.isTourPossible())
			{
				courier.setTour(tourGenerator.getTour());
				carte.addCourier(courier.getId(),courier);
				carte.addRequest(request);
				carte.notifyObservers();		
			}
			else
			{
				courier.deleteRequestCourier(request);
				MapView.impossibleTourPopUp.setVisible(true);
			}
			controller.setCurrentState(controller.TOUR_GENERATED_STATE);
		}
		else
		{
			carte.addCourier(courier.getId(),courier);
			carte.addRequest(request);
			carte.notifyObservers();
			controller.setCurrentState(controller.REQUESTS_LOADED_STATE);
		}	


		// Update View
		controller.window.drawRequests();
	}

	@Override
	public void undoCommand() {
		// Delete request from Courier's requestList and update its tour and value in map
		courier.deleteRequestCourier(request);
		if (courier.getRequestList().isEmpty())
		{
			courier.setTour(null);
		} else if (courier.getTour() != null)
		{
			GenerateTour tourGenerator = new GenerateTour(courier,carte);
        	courier.setTour(tourGenerator.getTour());
		}		
		carte.deleteRequest(request);
		carte.addCourier(courier.getId(),courier);
		carte.notifyObservers();

		// Handle controller's state
		if (!carte.hasTours())
		{
			controller.setCurrentState(controller.REQUESTS_LOADED_STATE);
		}
		else
		{
			controller.setCurrentState(controller.TOUR_GENERATED_STATE);
		}
		// Update View
		controller.window.mapView.removeInformation();

		controller.window.eraseRequests();
		controller.window.drawRequests();
        controller.window.refreshWindow();
	}

}
