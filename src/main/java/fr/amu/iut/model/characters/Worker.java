package fr.amu.iut.model.characters;

import fr.amu.iut.util.GameEvents;

/**
 * Interface représentant une capacité de travail pour un personnage.
 */
public interface Worker {

    /**
     * Permet à un personnage d'effectuer une tâche de travail.
     */
    void work();

    /**
     * Permet à un personnage de prendre une pause.
     * Affiche un message indiquant que le travailleur prend une pause.
     */
    default void takeBreak() {
        GameEvents.log("Le travailleur prend une petite pause bien méritée.");
    }
}
