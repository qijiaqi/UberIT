package app.main.view;

import app.main.controller.Controller;
import app.main.model.Intersection;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class MouseListener extends MouseAdapter {

	private final Controller controller;
	private final MapView mapView;
	private final Window window;

	public MouseListener(Controller c, MapView mv, Window w) {
		this.controller = c;
		this.mapView = mv;
		this.window = w;
	}

	@Override
	public void mouseClicked(MouseEvent evt) {
		// Method called by the mouse listener each time the mouse is clicked
		switch (evt.getButton()) {
			case MouseEvent.BUTTON1: {
				controller.leftClick();
				Intersection i = position(evt);
				if (i != null)
				{
					if (i.getId()==mapView.getCarte().getWarehouse().getId())
					{
						MapView.wareHouseClickedPopUp.setVisible(true);
					}
					else
					{
						controller.getClickedIntersection(i);
					}
				}
			}
			default: {
			}
		}
	}

	private Intersection position(MouseEvent evt) {
		MouseEvent e = SwingUtilities.convertMouseEvent(window, evt, mapView);
		int eX = e.getX();
		int eY = e.getY();
		if (eX > mapView.getWidth() || eX < 0 || eY > mapView.getHeight() || eY < 0) {
			return null;
		} else {
			return mapView.convertPixelToIntersection(e.getX(), e.getY());
		}
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		// if(e.getWheelRotation()<0){
		// mapView.setZoomFactor(1.1*mapView.getZoomFactor());
		// mapView.revalidate();
		// mapView.repaint();
		// window.refreshWindow();
		// }
		// //Zoom out
		// if(e.getWheelRotation()>0){
		// mapView.setZoomFactor(mapView.getZoomFactor()/1.1);
		// mapView.repaint();
		//
		// }
	}

}