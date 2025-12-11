package fr.amu.iut.util;

/**
 * Interface pour écouter les événements du jeu (logs, actions, etc.).
 * Le Modèle ne connaît que cette interface, pas la ConsoleView.
 */
public interface GameLogger {
    void log(String message);
}