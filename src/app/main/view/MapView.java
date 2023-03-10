package app.main.view;

import app.main.controller.Controller;
import app.main.model.*;
import app.main.observer.Observable;
import app.main.observer.Observer;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.util.List;
import java.util.*;

public class MapView extends JPanel implements Observer {
    private Carte carte;
    protected static final Dimension MAP_VIEW_DIMENSION = new Dimension(840, 700);
    protected static final Color MAP_COLOR = new Color(210, 210, 210);

    private static final long serialVersionUID = 1L;
    private double zoomFactor = 1;
    private boolean zoomer = false;
    private JPanel requestForm;
    private JPanel deleteCourierForm;
    public static JPanel wareHouseClickedPopUp;
    public static JPanel impossibleTourPopUp;
    private JPanel courierForm;
    private JRadioButton timeWindow1;
    private JRadioButton timeWindow2;
    private JRadioButton timeWindow3;
    private JRadioButton timeWindow4;
    private ButtonGroup bg;
    private JComboBox jComboBoxLivreur;
    public static JButton boutonValiderRequete;
    public static JButton boutonAnnuler;
    public static JButton boutonValiderCourier;
    public static JButton boutonValiderDeleteCourier;
    public static JButton wareHousePopUpCloseButton;
    public static JButton impossibleTourPopUpCloseButton;

    public JTextPane information;
    public JPanel informationPanel;
    private ButtonListener buttonListener;
    private Controller controller;
    private Window window;
    private int scale;
    private int viewHeight;
    private int viewWidth;
    private Graphics g;
    private Map<String, List<IntersectionButton>> mapButtons;
    public static JButton suppButton;
    public static JButton modifButton;

    public static List<IntersectionButton> intersectionButtons;
    private Map<Long, Color> couriersColors;

    /**
     * Create the map view with scale s in window w
     *
     * @param carte the map
     * @param s     the scale
     */
    public MapView(Carte carte, int s, Controller controller, Window window) {
        super();
        carte.addObserver(this);
        this.scale = s;
        this.mapButtons = new HashMap<>();
        setLayout(null);
        this.setMaximumSize(new Dimension(MAP_VIEW_DIMENSION));
        this.setMinimumSize(new Dimension(MAP_VIEW_DIMENSION));
        this.setPreferredSize(new Dimension(MAP_VIEW_DIMENSION));
        this.setBackground(MAP_COLOR);
        this.viewWidth = (int) MAP_VIEW_DIMENSION.getWidth();
        this.viewHeight = (int) MAP_VIEW_DIMENSION.getHeight();
        this.carte = carte;
        this.controller = controller;
        this.window = window;
        // buttonListener
        this.buttonListener = new ButtonListener(controller);

        this.intersectionButtons = new ArrayList<IntersectionButton>();

        // Formulaire pour creer une requete
        requestForm = new JPanel();
        requestForm.setBounds(viewWidth / 2 - 150, viewHeight / 2 - 150, 170, 180);
        requestForm.setBackground(Color.lightGray);
        boutonValiderRequete = new JButton("Valider");
        boutonValiderRequete.setBounds(10, 180, 80, 30);
        boutonValiderRequete.setBackground(Color.WHITE);
        boutonValiderRequete.addActionListener(buttonListener);
        requestForm.add(boutonValiderRequete);

        boutonAnnuler = new JButton("Annuler");
        boutonAnnuler.setBounds(100, 180, 80, 30);
        boutonAnnuler.setBackground(Color.WHITE);
        boutonAnnuler.addActionListener(buttonListener);
        requestForm.add(boutonAnnuler);

        JLabel label = new JLabel("1 - Plage Horaire", JLabel.CENTER);
        JLabel labelLivreur = new JLabel("2 - Choix du Livreur", JLabel.CENTER);
        label.setBounds(20, 0, 200, 80);
        labelLivreur.setBounds(20, 180, 200, 80);
        // Créer les boutons radio heure
        timeWindow1 = new JRadioButton("8-9h");
        timeWindow2 = new JRadioButton("9-10h");
        timeWindow3 = new JRadioButton("10-11h");
        timeWindow4 = new JRadioButton("11-12h");

        // Définir la position des boutons radio heures
        timeWindow1.setBounds(40, 60, 150, 50);
        timeWindow2.setBounds(40, 100, 150, 50);
        timeWindow3.setBounds(40, 140, 150, 50);
        timeWindow4.setBounds(40, 140, 150, 50);

        // Ajouter les boutons radio au groupe
        bg = new ButtonGroup();

        bg.add(timeWindow1);
        bg.add(timeWindow2);
        bg.add(timeWindow3);
        bg.add(timeWindow4);

        timeWindow1.setSelected(true);

        // Ajouter les boutons au frame
        requestForm.add(label);
        requestForm.add(timeWindow1);
        requestForm.add(timeWindow2);
        requestForm.add(timeWindow3);
        requestForm.add(timeWindow4);

        List<String> listLivreur = listLivreur(carte.getCourierMap().values());

        String[] arrLivreur = new String[listLivreur.size()];

        // Conversion de la liste en tableau
        listLivreur.toArray(arrLivreur);
        jComboBoxLivreur = new JComboBox<>(arrLivreur);
        jComboBoxLivreur.setBounds(40, 180, 150, 50);
        requestForm.add(labelLivreur);

        requestForm.setVisible(false);
        this.add(requestForm);

        this.wareHouseClickedPopUp = new JPanel();
        wareHouseClickedPopUp.setBounds(viewWidth / 2 - 300, viewHeight / 2 - 150, 600, 180);
        wareHouseClickedPopUp.setBackground(Color.lightGray);
        ;
        JLabel popUpMessage = new JLabel(
                " L'intersection choisie est la WareHouse. Veuillez choisir une autre intersection. ", JLabel.CENTER);
        popUpMessage.setBounds(20, 0, 200, 80);
        wareHousePopUpCloseButton = new JButton(" Ok ");
        wareHousePopUpCloseButton.addActionListener(buttonListener);
        wareHousePopUpCloseButton.setBounds(50, 250, 80, 30);
        wareHousePopUpCloseButton.setBackground(Color.WHITE);
        wareHouseClickedPopUp.add(popUpMessage);
        wareHouseClickedPopUp.add(wareHousePopUpCloseButton);
        wareHouseClickedPopUp.setVisible(false);
        this.add(wareHouseClickedPopUp);

        this.impossibleTourPopUp = new JPanel();
        impossibleTourPopUp.setBounds(viewWidth / 2 - 300, viewHeight / 2 - 150, 600, 180);
        impossibleTourPopUp.setBackground(Color.lightGray);
        ;
        JLabel impossibleTourpopUpMessage = new JLabel(" Tour impossible", JLabel.CENTER);
        impossibleTourpopUpMessage.setBounds(20, 0, 200, 80);
        impossibleTourPopUpCloseButton = new JButton(" Ok ");
        impossibleTourPopUpCloseButton.addActionListener(buttonListener);
        impossibleTourPopUpCloseButton.setBounds(50, 250, 80, 30);
        impossibleTourPopUpCloseButton.setBackground(Color.WHITE);
        impossibleTourPopUp.add(impossibleTourpopUpMessage);
        impossibleTourPopUp.add(impossibleTourPopUpCloseButton);
        impossibleTourPopUp.setVisible(false);
        this.add(impossibleTourPopUp);

        this.informationPanel = new JPanel();

        this.information = new JTextPane();
        informationPanel.setBackground(new Color(248, 247, 252));
        information.setBackground(new Color(248, 247, 252));
        information.setFont(new Font("poppins", Font.BOLD, 10));
        information.setEditable(false);

        StyledDocument doc = information.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        // Ok pour Add Courier
        courierForm = new JPanel();
        courierForm.setBounds(viewWidth / 2 - 150, viewHeight / 2 - 150, 150, 150);
        courierForm.setBackground(Color.lightGray);
        JLabel labelCourier = new JLabel("Livreur rajouté", JLabel.CENTER);
        labelCourier.setBounds(20, 0, 200, 80);
        courierForm.add(labelCourier);
        boutonValiderCourier = new JButton("Fermer");
        boutonValiderCourier.setBounds(50, 110, 80, 30);
        boutonValiderCourier.setBackground(Color.WHITE);
        boutonValiderCourier.addActionListener(buttonListener);
        courierForm.add(boutonValiderCourier);
        courierForm.setVisible(false);
        this.add(courierForm);

        // Delete Courier
        deleteCourierForm = new JPanel();
        deleteCourierForm.setBounds(viewWidth / 2 - 150, viewHeight / 2 - 150, 150, 100);
        deleteCourierForm.setBackground(Color.lightGray);
        JLabel labelDel = new JLabel("Supprimer le livreur", JLabel.CENTER);
        labelCourier.setBounds(20, 0, 200, 80);
        deleteCourierForm.add(labelDel);
        boutonValiderDeleteCourier = new JButton("Supprimer Livreur ");
        boutonValiderDeleteCourier.setBounds(50, 110, 80, 30);
        boutonValiderDeleteCourier.setBackground(Color.WHITE);
        boutonValiderDeleteCourier.addActionListener(buttonListener);
        deleteCourierForm.add(boutonValiderDeleteCourier);
        deleteCourierForm.add(jComboBoxLivreur);
        deleteCourierForm.setVisible(false);
        this.add(deleteCourierForm);

    }

    /**
     * Method called each this must be redrawn
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g1 = (Graphics2D) g;
        this.g = g;
        // Drawing all segments
        List<Segment> segments = carte.getSegmentList();
        Map<Long, Intersection> intersections = carte.getMapIntersections();

        for (Segment s : segments) {
            Intersection start = intersections.get(s.getIdOrigin());
            Intersection end = intersections.get(s.getIdDestination());
            Pixel pStart = convertIntersectionToPixel(start);
            Pixel pEnd = convertIntersectionToPixel(end);
            paintSegment(g1, pStart, pEnd, new Color(248, 247, 252));
        }

        g1.setColor(new Color(52, 46, 55));

        if (carte.getWarehouse() != null) {
            Pixel p = convertIntersectionToPixel(carte.getWarehouse());
            g1.setColor(Color.RED);
            g1.fillOval(p.getX(), p.getY(), 8, 8);
        }

        for (Map.Entry<Long, Courier> courierKV : carte.getCourierMap().entrySet()) {
            Courier courier = courierKV.getValue();
            if (window.topBar.displayedCouriers.get(courier.getId())) {
                if (courier.getTour() != null) {
                    Tour tour = courier.getTour();
                    for (Segment s : tour.getPath()) {
                        Intersection start = intersections.get(s.getIdOrigin());
                        Intersection end = intersections.get(s.getIdDestination());
                        Pixel pStart = convertIntersectionToPixel(start);
                        Pixel pEnd = convertIntersectionToPixel(end);
                        paintOrientedSegment(g1, pStart, pEnd, Window.couriersColors.get(courier.getId()));
                    }
                }
            }
        }

        if (zoomer == true) {
            AffineTransform at = new AffineTransform();
            at.scale(zoomFactor, zoomFactor);
            zoomer = false;
            g1.transform(at);
        }
    }

    public void drawRequests() {
        Intersection i;
        if (!carte.getIntersectionList().isEmpty()) {
            for (Map.Entry<Long, Courier> courierKV : carte.getCourierMap().entrySet()) {
                Courier courier = courierKV.getValue();
                if (window.topBar.displayedCouriers.get(courier.getId())) {
                    if (controller.getCurrentState() != controller.TOUR_GENERATED_STATE) {
                    for (Request request : courier.getRequestList()) {
                        i = carte.getIntersectionById(request.getIntersectionId());
                        Pixel p = convertIntersectionToPixel(i);
                        IntersectionButton iButton = new IntersectionButton(this.carte, i,
                                Window.couriersColors.get(courier.getId()));
                        iButton.setRequest(request);
                        iButton.setBounds(p.getX() - 4, p.getY() - 4, 16, 16);
                        iButton.addActionListener(this.buttonListener);
                        this.add(iButton);
                        this.intersectionButtons.add(iButton);
                    }
                    }
                    if (controller.getCurrentState() == controller.TOUR_GENERATED_STATE && courier.getTour()!=null) {
                    for (Delivery d : courier.getTour().getOrderedDeliveries()) {
                    i = carte.getIntersectionById(d.getIntersectionId());
                    if (i.getId() != carte.getWarehouse().getId())
                    {
                        Pixel p = convertIntersectionToPixel(i);
                        IntersectionButton iButton = new IntersectionButton(this.carte, i,
                        Window.couriersColors.get(courier.getId()));
                        iButton.setDelivery(d);
                        iButton.setBounds(p.getX() - 4, p.getY() - 4, 16, 16);
                        iButton.addActionListener(this.buttonListener);
                        this.add(iButton);
                        this.intersectionButtons.add(iButton);
                    }
                    
                    }
                    }
                }
            }
        }
        this.mapButtons.put("REQUEST", this.intersectionButtons);
    }

    public void setDeliveryOnButtons(Tour tour) {
        for (Delivery d : tour.getOrderedDeliveries()) {
            IntersectionButton iButton = getButtonByIntersection(d.getIntersectionId());
            iButton.setRequest(null);
            iButton.setDelivery(d);
        }
        this.revalidate();
        this.repaint();
        this.window.refreshWindow();
    }

    public void displayInformation(IntersectionButton iButton) {
        Intersection i = iButton.getIntersection();
        Pixel p = convertIntersectionToPixel(i);
        this.information.setText(iButton.getInformation());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(248, 247, 252));
        modifButton = new JButton(new ImageIcon("src/assets/edit.png"));
        modifButton.setBackground(Window.WINDOW_FOREGROUND_COLOR);
        modifButton.addActionListener(this.buttonListener);
        modifButton.setPreferredSize(new Dimension(32, 20));
        modifButton.setMinimumSize(new Dimension(32, 20));
        modifButton.setMaximumSize(new Dimension(32, 20));

        suppButton = new JButton(new ImageIcon("src/assets/delete.png"));
        suppButton.setBackground(Window.WINDOW_FOREGROUND_COLOR);
        suppButton.addActionListener(this.buttonListener);
        suppButton.setPreferredSize(new Dimension(32, 20));
        suppButton.setMinimumSize(new Dimension(32, 20));
        suppButton.setMaximumSize(new Dimension(32, 20));

        buttonPanel.add(modifButton);
        buttonPanel.add(suppButton);

        this.informationPanel.setBounds((p.getX() - 96), (p.getY() - 104), 192, 96);
        this.informationPanel.add(information);
        this.informationPanel.add(buttonPanel);
        this.add(informationPanel);
        this.revalidate();
        this.repaint();
    }

    public void removeInformation() {
        this.informationPanel.removeAll();
        this.remove(informationPanel);
    }

    public void eraseRequest() {
        for (IntersectionButton i : intersectionButtons) {
            this.remove(i);
        }
        this.revalidate();
        this.repaint();
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int e) {

    }

    public Pixel convertIntersectionToPixel(Intersection i) {
        int x = (int) ((i.getLongitude() - carte.getMinLong()) / (carte.getMaxLong() - carte.getMinLong())
                * viewWidth);
        int y = (int) ((i.getLatitude() - carte.getMinLat()) / (carte.getMaxLat() - carte.getMinLat())
                * viewHeight);
        return new Pixel(x, y);
    }

    public void paintOrientedSegment(Graphics2D g, Pixel p1, Pixel p2, Color c) {
        g.setColor(c);
        g.setStroke(new BasicStroke(2.0f,
                BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_ROUND));
        int p1X = p1.getX() + 5;
        int p1Y = p1.getY() + 5;
        int p2X = p2.getX() + 5;
        int p2Y = p2.getY() + 5;

        int headAngle = 30;
        int headLength = 10;
        double offs = headAngle * Math.PI / 180.0;
        double angle = Math.atan2(p1Y - p2Y, p1X - p2X);
        int[] xs = { p2X + (int) (headLength * Math.cos(angle + offs)), p2X,
                p2X + (int) (headLength * Math.cos(angle - offs)) };
        int[] ys = { p2Y + (int) (headLength * Math.sin(angle + offs)), p2Y,
                p2Y + (int) (headLength * Math.sin(angle - offs)) };
        g.drawLine(p1X, p1Y, p2X, p2Y);
        g.drawPolyline(xs, ys, 3);
    }

    public void paintSegment(Graphics2D g, Pixel p1, Pixel p2, Color c) {
        g.setColor(c);
        g.setStroke(new BasicStroke(2.0f,
                BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_ROUND));
        int p1X = p1.getX() + 5;
        int p1Y = p1.getY() + 5;
        int p2X = p2.getX() + 5;
        int p2Y = p2.getY() + 5;

        g.drawLine(p1X, p1Y, p2X, p2Y);
    }

    /**
     * Method called by objects observed by this each time they are modified
     */
    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }

    public Intersection convertPixelToIntersection(int x, int y) {
        double lat = ((double) y / viewHeight) * (carte.getMaxLat() - carte.getMinLat()) + carte.getMinLat();
        double lon = ((double) x / viewWidth) * (carte.getMaxLong() - carte.getMinLong()) + carte.getMinLong();
        Intersection i = null;

        Map<Long, Intersection> intersections = carte.getMapIntersections();
        double minDistance = Long.MAX_VALUE;

        for (Map.Entry<Long, Intersection> m : intersections.entrySet()) {
            Intersection intersection = m.getValue();
            if (distance(intersection, lat, lon) < minDistance) {
                minDistance = distance(intersection, lat, lon);
                i = intersection;
            }
        }
        return i;
    }

    public void setZoomFactor(double factor) {
        if (factor < this.zoomFactor) {
            this.zoomFactor = this.zoomFactor / 1.1;
        } else {
            this.zoomFactor = factor;
        }
        this.zoomer = true;
    }

    public double getZoomFactor() {
        return zoomFactor;
    }

    public double distance(Intersection i, double lat, double lon) {
        return Math.sqrt((Math.pow(i.getLatitude() - lat, 2) + Math.pow(i.getLongitude() - lon, 2)));
    }

    public void afficherFormRequest() {
        requestForm.add(jComboBoxLivreur);

        this.requestForm.setVisible(true);
    }

    public void fermerFormRequest() {
        this.requestForm.setVisible(false);
    }

    public void afficherFormCourier(long idLivreur) {
        String livreur = "Livreur" + idLivreur;
        jComboBoxLivreur.addItem(livreur);
        this.courierForm.setVisible(true);
    }

    public void fermerFormCourier() {
        this.courierForm.setVisible(false);
    }

    public void afficherDeleteCourier() {
        deleteCourierForm.add(jComboBoxLivreur);

        this.deleteCourierForm.setVisible(true);

    }

    public void fermerDeleteCourier() {
        this.deleteCourierForm.setVisible(false);
        jComboBoxLivreur.removeItemAt(jComboBoxLivreur.getSelectedIndex());
    }

    public long getDelCourier() {
        long idToDel = getLivreurSelected();
        return idToDel;

    }

    public String getChampHeure() {
        String heure = "";
        if (timeWindow1.isSelected()) {
            // bg.clearSelection();
            return "8";

        } else if (timeWindow2.isSelected()) {
            // bg.clearSelection();
            // timeWindow2.setSelected(false);
            return "9";
        } else if (timeWindow3.isSelected()) {
            // bg.clearSelection();
            // timeWindow3.setSelected(false);
            return "10";
        } else if (timeWindow4.isSelected()) {
            // bg.clearSelection();
            // timeWindow4.setSelected(false);
            return "11";
        }
        return heure;
    }

    public List<String> listLivreur(Collection<Courier> courierList) {
        List<String> listLivreur = new ArrayList<>();
        for (Courier courier : courierList) {
            listLivreur.add("Livreur" + courier.getId());
        }
        return listLivreur;
    }

    public long getLivreurSelected() {
        long idLivreur;
        String choix = jComboBoxLivreur.getSelectedItem().toString().replaceAll("[^\\d]", "");
        idLivreur = Long.parseLong(choix);
        return idLivreur;
    }

    public IntersectionButton getButtonByRequest(Request request) {
        for (IntersectionButton intersectionButton : intersectionButtons) {
            if (intersectionButton.getRequest().equals(request)) {
                return intersectionButton;
            }
        }
        return null;
    }

    public IntersectionButton getButtonByDelivery(Delivery delivery) {
        for (IntersectionButton intersectionButton : intersectionButtons) {
            if (intersectionButton.getDelivery()!=null)
            {
                if (intersectionButton.getDelivery().getIntersectionId() == delivery.getIntersectionId()) {
                return intersectionButton;
                }
            }
            
        }
        return null;
    }

    public IntersectionButton getButtonByIntersection(Long intersectionId) {
        for (IntersectionButton intersectionButton : intersectionButtons) {
            if (intersectionButton.getIntersection().getId() == intersectionId) {
                return intersectionButton;
            }
        }
        return null;
    }

    public Carte getCarte() {
        return carte;
    }

}
