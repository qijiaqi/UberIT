package app.main.view;

import javax.swing.*;
import java.awt.*;

/**
 * Component used for the menu in the LeftBar
 */
public class ActionButton extends JButton {


    protected static final Dimension ACTION_BUTTON_DIMENSION = new Dimension(140, 48);

    /**
     * Constructor of the ActionButton Component
     * @param String text
     */
    public ActionButton(String text) {
        super(text);
        this.setBackground(Window.WINDOW_FOREGROUND_COLOR);
        this.setForeground(Window.WINDOW_BACKGROUND_COLOR);
        this.setFont(Window.TEXT_FONT);
        this.setPreferredSize(ACTION_BUTTON_DIMENSION);
        this.setMaximumSize(ACTION_BUTTON_DIMENSION);
        this.setMinimumSize(ACTION_BUTTON_DIMENSION);
    }
}
