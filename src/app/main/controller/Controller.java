package app.main.controller;

import java.io.File;

import app.main.model.Carte;
import app.main.model.Courier;
import app.main.model.Intersection;
import app.main.view.IntersectionButton;
import app.main.view.Window;
import app.main.xml.FileChooser;
import app.main.xml.XMLdeserializer;
import app.main.xml.XMLserializer;

public class Controller {

	public Carte carte;
	public Window window;
	public Courier courier;
	private ListOfCommands listOfCommands;
	private State currentState;
	public InitialState INITIAL_STATE = new InitialState();
	public LoadedMapState LOADED_MAP_STATE = new LoadedMapState();
	public addRequestState1 ADD_REQUEST_STATE1 = new addRequestState1();
	public addRequestState2 ADD_REQUEST_STATE2 = new addRequestState2();
	public RequestsLoadedState REQUESTS_LOADED_STATE = new RequestsLoadedState();
	public TourGeneratedState TOUR_GENERATED_STATE = new TourGeneratedState();
	public InfoDisplayedState INFO_DISPLAYED_STATE = new InfoDisplayedState();
	public ModifRequestState MODIF_REQUEST_STATE = new ModifRequestState();
	public addCourierState ADD_COURIER_STATE = new addCourierState();
	public deleteCourierState DELETE_COURIER_STATE = new deleteCourierState();

	/**
	 * Create the controller of the application
	 *
	 * @param carte the map
	 */
	public Controller(Carte carte, int scale) {
		this.carte = carte;
		listOfCommands = new ListOfCommands();
		window = new Window(carte, scale, this);
		this.setCurrentState(INITIAL_STATE);
	}

	public State getCurrentState() {
		return currentState;
	}

	public void setCurrentState(State newState) {
		this.currentState = newState;
		this.currentState.majButtons(this.window);
	}

	public void selectionnerCarte() {
		try {
			currentState.loadMap(this, carte,listOfCommands);
		} catch (Exception e) {
			// if (e != Carte.) {
			// JOptionPane.showMessageDialog(new JFrame(),
			// "Erreur : fichier invalide",
			// "Erreur",
			// JOptionPane.ERROR_MESSAGE);
			// } else System.err.println(e.getMessage());
		}
	}

	public void selectionnerRequete() {
		try {
			currentState.loadRequest(this, carte);
		} catch (Exception e) {
			// if (e != Carte.) {
			// JOptionPane.showMessageDialog(new JFrame(),
			// "Erreur : fichier invalide",
			// "Erreur",
			// JOptionPane.ERROR_MESSAGE);
			// } else System.err.println(e.getMessage());
		}
	}

	public void loadTour() {
		try {
			currentState.loadTour(this, carte);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void addRequest() {
		try {
			currentState.addRequest(this, carte);
		} catch (Exception e) {
			// if (e != Carte.) {
			// JOptionPane.showMessageDialog(new JFrame(),
			// "Erreur : fichier invalide",
			// "Erreur",
			// JOptionPane.ERROR_MESSAGE);
			// } else System.err.println(e.getMessage());
		}
	}

	public void validerRequest() {
		currentState.validerRequest(this, window, carte, listOfCommands);
	}

	public void modifRequest() {
		currentState.modifRequest(this, window, carte);
	}

	public void annulerAction() {
		currentState.annulerAction(this, window, carte);
	}

	public void getClickedIntersection(Intersection i) {
		try {
			currentState.selectionnerIntersection(this, window, carte, listOfCommands, i);
		} catch (Exception e) {
		}
	}

	public void getChampRequest() {
		try {
			currentState.getChampRequest(this, window);
		} catch (Exception e) {
		}
	}

	public void displayInformation(IntersectionButton intersectionButton) {
		currentState.displayInformation(this,carte, intersectionButton);
	}

	public void deleteRequest() {
		currentState.deleteRequest(this, carte, listOfCommands);
	}

	public void saveRequest() {
		try {
			File file = FileChooser.selectFile();
			if (file != null) {
				XMLserializer serializer = new XMLserializer();
				serializer.save(carte.getRequestList(), file);
			}

		} catch (Exception e) {
		}
	}

	public void addCourier() {
		try {
			currentState.addCourier(this, window, carte, listOfCommands);
		} catch (Exception e) {
		}
	}

	public void validerCourier() {
		try {
			currentState.validerCourier(this, window, carte,listOfCommands);
		} catch (Exception e) {
		}
	}

	public void deleteCourier() {
		try {
			currentState.deleteCourier(this, window);
		} catch (Exception e) {
		}
	}

	public void validerDelCourier() {
		try {
			currentState.validerDelCourier(this, window, carte,listOfCommands);
		} catch (Exception e) {
		}
	}

	public void displayCourier(Long courrierId) {
		currentState.displayCourier(this, courrierId);
	}

	public void undo() {
		currentState.undo(listOfCommands);
	}

	public void redo() {
		currentState.redo(listOfCommands);
	}

	public void leftClick() {
		currentState.removeInformation(this);
	}
	public void setListOfCmds(ListOfCommands listOfCmds)
	{
		this.listOfCommands = listOfCmds;
	}
}