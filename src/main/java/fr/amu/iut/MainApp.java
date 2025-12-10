package fr.amu.iut;

import fr.amu.iut.controller.GameController;

/**
 * Classe principale pour démarrer l'application.
 */
public class MainApp {

    public static void main(String[] args) {
        GameController game = new GameController();
        game.start();
    }
}