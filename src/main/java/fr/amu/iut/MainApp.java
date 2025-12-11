package fr.amu.iut;

import fr.amu.iut.controller.GameController;

/**
 * Classe principale pour démarrer l'application.
 */
public class MainApp {

    /**
     * Point d'entrée de l'application.
     *
     * @param args Arguments de la ligne de commande (non utilisés).
     */
    public static void main(String[] args) {
        GameController game = new GameController();
        game.start();
    }
}