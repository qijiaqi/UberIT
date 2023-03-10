package app.main;

import app.main.controller.Controller;
import app.main.model.Carte;
import app.main.model.Courier;

public class Main {

    public static void main(String args[]) throws Exception {
        Carte map = new Carte();
        Controller c = new Controller(map, 3);
    }

}
