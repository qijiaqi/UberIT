package app.main.view;

import app.main.controller.Controller;
import app.main.model.Carte;
import app.main.model.Courier;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Window extends JFrame {

    protected static final Dimension WINDOW_DIMENSION = new Dimension(1024, 768);
    protected static final Color WINDOW_BACKGROUND_COLOR = new Color(248, 247, 252);
    protected static final Color WINDOW_FOREGROUND_COLOR = new Color(78, 128, 152);
    protected static final Font TITLE_FONT = new Font("Poppins", Font.BOLD, 24);
    protected static final Font TEXT_FONT = new Font("Poppins", Font.BOLD, 12);
    protected static final Map<Long, Color> couriersColors = new HashMap<>();

    public JPanel mainWrapper;
    public LeftBar leftBar;
    public TopBar topBar;
    public MapView mapView;

    public Window(Carte map, int s, Controller controller) {
        super("UberIT");
        this.setSize(WINDOW_DIMENSION);

        couriersColors.put(1L, new Color(39, 93, 173));
        couriersColors.put(2L, new Color(246, 164, 46));
        couriersColors.put(3L, new Color(247, 23, 53));
        couriersColors.put(4L, new Color(240, 135, 0));
        couriersColors.put(5L, new Color(34, 111, 84));
        couriersColors.put(6L, new Color(115, 82, 144));
        couriersColors.put(7L, new Color(158, 71, 112));

        mainWrapper = new JPanel(new BorderLayout());
        mainWrapper.setPreferredSize(WINDOW_DIMENSION);
        mainWrapper.setBackground(WINDOW_BACKGROUND_COLOR);

        leftBar = new LeftBar(controller);
        topBar = new TopBar(map, controller);
        mapView = new MapView(map, s, controller, this);
        mainWrapper.add(leftBar, BorderLayout.WEST);
        mainWrapper.add(topBar, BorderLayout.NORTH);
        mainWrapper.add(mapView, BorderLayout.CENTER);
        MouseListener mouseListener = new MouseListener(controller, mapView, this);
        addMouseListener(mouseListener);
        this.add(mainWrapper);
        this.addMouseListener(new MouseListener(controller, this.mapView, this));
        this.addMouseWheelListener(new MouseListener(controller, this.mapView, this));
        this.setVisible(true);
    }

    public void drawRequests() {
        this.mapView.drawRequests();
        this.refreshWindow();
    }

    public void eraseRequests() {
        this.mapView.eraseRequest();
        this.refreshWindow();
    }

    public void displayInformation(IntersectionButton iButton) {
        this.mapView.displayInformation(iButton);
        this.refreshWindow();
    }

    public void refreshWindow() {
        this.mainWrapper.revalidate();
        this.mainWrapper.repaint();
        this.revalidate();
        this.repaint();
    }

    public void addCourier(Courier courier) {
        Random rand = new Random();
	    float red = rand.nextFloat();
	    float green = rand.nextFloat();
	    float blue = rand.nextFloat();
	    Color randomColor = new Color(red, green, blue);
        Window.couriersColors.put(courier.getId(), randomColor);
        topBar.addCourier(courier);
        this.refreshWindow();
    }

    public void deleteCourier(long idToDel) {
        topBar.deleteCourier(idToDel);
        this.refreshWindow();
    }
}
