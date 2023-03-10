package app.main.view;

import app.main.controller.Controller;
import app.main.model.Carte;
import app.main.model.Courier;
import app.main.observer.Observable;
import app.main.observer.Observer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class TopBar extends JPanel {

    protected static final Dimension TOP_PANEL_DIMENSION = new Dimension(1024, 76);
    public JPanel couriersPanel;
    private Carte carte;
    public Map<Long, Boolean> displayedCouriers = new HashMap<>();
    public Map<Long, JButton> couriersButtons = new HashMap<>();
    private ButtonListener buttonListener;

    public TopBar(Carte carte, Controller c) {
        super();
        this.carte = carte;
        this.buttonListener = new ButtonListener(c);
        setLayout(new BorderLayout());
        this.setMaximumSize(TOP_PANEL_DIMENSION);
        this.setMinimumSize(TOP_PANEL_DIMENSION);
        this.setPreferredSize(TOP_PANEL_DIMENSION);
        JLabel labelTitle = new JLabel("UberIT");
        labelTitle.setFont(Window.TITLE_FONT);
        labelTitle.setBorder(new EmptyBorder(0, 24, 0, 0));
        labelTitle.setForeground(Window.WINDOW_FOREGROUND_COLOR);
        this.createCouriersPanel();
        this.add(labelTitle, BorderLayout.WEST);
    }

    public void createCouriersPanel() {
        couriersPanel = new JPanel(new FlowLayout());
        couriersPanel.setBorder(new EmptyBorder(24, 0, 24, 0));
        couriersPanel.setBounds(192, 0, 837, 76);
        for (Courier courier : carte.getCourierMap().values()) {
            JButton button = new JButton("Livreur " + courier.getId());
            displayedCouriers.put(courier.getId(), true);
            button.setBackground(Window.couriersColors.get(courier.getId()));
            couriersButtons.put(courier.getId(), button);
            button.addActionListener(this.buttonListener);
            couriersPanel.add(button);
        }

        this.add(couriersPanel);
    }

    public void addCourier(Courier courier) {
        JButton button = new JButton("Livreur " + courier.getId());
        displayedCouriers.put(courier.getId(), true);
        button.setBackground(Window.couriersColors.get(courier.getId()));
        couriersButtons.put(courier.getId(), button);
        button.addActionListener(this.buttonListener);
        couriersPanel.add(button);
        displayedCouriers.put(courier.getId(), true);
    }

    public void deleteCourier(long idToDel) {
        displayedCouriers.remove(idToDel);
        couriersPanel.remove(couriersButtons.get(idToDel));
        couriersButtons.remove(idToDel);

    }

    public void displayCourier(Long courierId) {
        JButton button = couriersButtons.get(courierId);
        if (displayedCouriers.get(courierId)) {
            button.setBackground(Color.white);
            button.revalidate();
            button.repaint();

            displayedCouriers.put(courierId, false);
        } else {
            button.setBackground(Window.couriersColors.get(courierId));
            button.revalidate();
            button.repaint();
            displayedCouriers.put(courierId, true);
        }
    }

}
